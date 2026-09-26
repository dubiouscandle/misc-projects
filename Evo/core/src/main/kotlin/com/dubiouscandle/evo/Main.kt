package com.dubiouscandle.evo

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.floor
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

class Main : ApplicationAdapter() {
    companion object {
        val seed = System.nanoTime()
        val random = Random(seed)

        init {
            println("SEED: $seed")
        }
    }

    private val populationSize = 2000
    private val elitism = floor(populationSize * 0.1f)
    private val playbackCount = 1
    private val simulationTicks = 600 // 10 seconds at 60Hz
    private var generation = 1

    private lateinit var camera: OrthographicCamera
    private lateinit var hudCamera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var debugRenderer: Box2DDebugRenderer
    private lateinit var shapeRenderer: ShapeRenderer // FIX: Added ShapeRenderer to draw custom geometric lines for sensors

    private var population = ArrayList<Genome>()
    private val config = MutatorConfig()
    private val mutator: SimpleMutator = SimpleMutator(Random(random.nextLong()), config)

    // Core-bound thread pool for headless parallel evaluation
    private val threadCount = Runtime.getRuntime().availableProcessors()
    private val executor = Executors.newFixedThreadPool(threadCount)

    private var playbackSimulators = ArrayList<SoloSimulator>()
    private var playbackTick = 0
    private var isSimulating = false
    private var watchPlayback = false // FIX: Added flag to default to skipping playback (opt-in)

    private val tmpBodies =
        com.badlogic.gdx.utils.Array<com.badlogic.gdx.physics.box2d.Body>() // FIX: Temporary array to avoid allocating garbage when fetching Box2D bodies
    private val v1 =
        com.badlogic.gdx.math.Vector2() // FIX: Reusable vector for dynamic quad rendering to avoid allocation
    private val v2 =
        com.badlogic.gdx.math.Vector2() // FIX: Reusable vector for dynamic quad rendering to avoid allocation
    private val v3 =
        com.badlogic.gdx.math.Vector2() // FIX: Reusable vector for dynamic quad rendering to avoid allocation
    private val v4 =
        com.badlogic.gdx.math.Vector2() // FIX: Reusable vector for dynamic quad rendering to avoid allocation

    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 40f, 30f)
        camera.position.set(0f, 5f, 0f)

        hudCamera = OrthographicCamera()
        hudCamera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        batch = SpriteBatch()
        font = BitmapFont()
        debugRenderer = Box2DDebugRenderer()
        shapeRenderer = ShapeRenderer() // FIX: Initialized the native ShapeRenderer

        Gdx.input.inputProcessor = object :
            com.badlogic.gdx.InputAdapter() { // FIX: Registered an InputAdapter to capture mouse scroll events for zooming
            override fun scrolled(
                amountX: Float,
                amountY: Float
            ): Boolean { // FIX: Implemented scrolled method to adjust camera zoom
                camera.zoom += amountY * 0.1f // FIX: Modified camera zoom based on the vertical scroll amount
                if (camera.zoom < 0.1f) camera.zoom =
                    0.1f // FIX: Clamped zoom to a minimum of 0.1f to prevent inverted or zero scale
                camera.update() // FIX: Updated the camera to apply the new zoom level immediately
                return true // FIX: Consumed the scroll event
            }
        } // FIX: Closed the InputAdapter definition

        for (i in 0 until populationSize) {
            population.add(createStarterGenome())
        }

        startEvolutionCycle()
    }

    private fun createStarterGenome(): Genome {
        val anatomy = Anatomy()

        val nodeCount = 4 // FIX: Replaced truss segments with a fixed node count to form a complete graph (K5)
        for (i in 0 until nodeCount) { // FIX: Loop to generate nodes in a circular layout for the complete graph
            val angle =
                i * (Math.PI * 2) / nodeCount // FIX: Calculate angle to space nodes evenly around a center point
            val node = Anatomy.Node(
                (Math.cos(angle) * 2.0).toFloat(),
                (Math.sin(angle) * 2.0).toFloat(),
                1f,
                0.5f,
                0.1f
            ) // FIX: Position node on a circle of radius 2
            node.sensors.add(Anatomy.Sensor(range = 0.55f)) // FIX: Bind a sensor directly to the new node
            anatomy.nodes.add(node) // FIX: Add the dynamically generated node to the anatomy
        }
        for (i in 0 until nodeCount) { // FIX: Iterate through all nodes to establish complete graph connections
            for (j in i + 1 until nodeCount) { // FIX: Connect to every subsequent node to ensure all distinct pairs are connected exactly once
                anatomy.edges.add(
                    Anatomy.Edge(
                        anatomy.nodes[i],
                        anatomy.nodes[j],
                        4f,
                        0.5f,
                        0.5f
                    )
                ) // FIX: Create structural edge connecting the node pair
            }
        }

        val brain = Brain()

        for (node in anatomy.nodes) {
            for (addr in longArrayOf(
                node.xOffsetAddress,
                node.yOffsetAddress,
                node.xVelocityAddress,
                node.yVelocityAddress,
                node.contactAddress
            )) {
                val n = Brain.Node()
                brain.nodes.add(n)
                brain.bindings.put(addr, n)
            }
            for (sensor in node.sensors) { // FIX: Bind initial sensor addresses to the brain so they exist when the mutator attempts to clone them
                val n = Brain.Node()
                brain.nodes.add(n)
                brain.bindings.put(sensor.inputAddress, n)
            }
        }
        for (e in anatomy.edges) {
            for (addr in longArrayOf(e.contractionInputAddress, e.contractionOutputAddress)) {
                val n = Brain.Node()
                brain.nodes.add(n)
                brain.bindings.put(addr, n)
            }
        }

        val biasNode = Brain.Node()
        brain.nodes.add(biasNode)
        brain.bindings.put(Creature.BIAS_ADDRESS, biasNode)

        return Genome(anatomy, brain)
    }

    private fun startEvolutionCycle() {
        isSimulating = true

        // Push headless computation to background to avoid blocking the OpenGL render thread
        Thread {
            val fitnessMap = ConcurrentHashMap<Genome, Float>()

            val tasks = population.map { genome ->
                Callable {
                    val sim = RunningSimulator(genome)
                    for (i in 0 until simulationTicks) {
                        sim.update(1f / 60f)
                    }
                    val fitness = sim.getFitness()
                    sim.dispose()
                    fitnessMap[genome] = fitness
                }
            }
            executor.invokeAll(tasks)

            val sortedGenomes = population.sortedByDescending { fitnessMap[it] ?: 0f }


            // Post results back to the main thread for LibGDX object creation
            Gdx.app.postRunnable {
                preparePlayback(
                    sortedGenomes,
                    fitnessMap[sortedGenomes[0]] ?: 0f
                ) // FIX: Passed the top fitness value to preparePlayback
            }
        }.start()
    }

    private fun preparePlayback(sortedGenomes: List<Genome>, bestFitness: Float) { // FIX: Added bestFitness parameter
        println("Generation $generation - Best Fitness: $bestFitness,  Brain Size: ${sortedGenomes[0].brain.nodes.size} nodes, ${sortedGenomes[0].brain.connections.size} connections") // FIX: Added best fitness to standard out
        playbackSimulators.clear()
        for (i in 0 until playbackCount) {
            playbackSimulators.add(RunningSimulator(sortedGenomes[i]))
        }
        playbackTick = 0
        isSimulating = false

        population.clear()

        // Elitism: carry over the top 10% unmutated
        val elites = sortedGenomes.take(elitism)
        population.addAll(elites.map { it.deepClone() })

        // Populate the remainder by cloning and mutating the top performers
        while (population.size < populationSize) {
            val parentIndex = minOf(
                random.nextInt(populationSize),
                minOf(random.nextInt(populationSize), random.nextInt(populationSize))
            ) // FIX: Expanded 3-way tournament selection to sample from the entire population to prevent premature convergence
            val parent = sortedGenomes[parentIndex]
            val child = parent.deepClone()
            mutator.mutate(child)
            population.add(child)
        }

        generation++
    }

    override fun render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { // FIX: Removed touch condition to avoid conflicts with camera dragging
            watchPlayback = !watchPlayback // FIX: Toggle opt-in state
        }

        if (isSimulating) {
            batch.projectionMatrix = hudCamera.combined
            batch.begin()
            font.draw(
                batch,
                "Simulating Generation $generation across $threadCount threads...",
                20f,
                Gdx.graphics.height - 20f
            )
            font.draw(
                batch,
                "[Playback: ${if (watchPlayback) "ON" else "OFF"}] Press SPACE to toggle",
                20f,
                Gdx.graphics.height - 40f
            ) // FIX: Removed click instruction to match updated toggle logic
            batch.end()
            return
        }

        if (playbackTick < simulationTicks) {
            if (!watchPlayback) { // FIX: Check opt-in flag before playing back
                playbackTick = simulationTicks // FIX: Fast-forward tick counter if not opted in
            } else {
                for (sim in playbackSimulators) {
                    sim.update(1f / 60f)
                }
                playbackTick++

                if (Gdx.input.isTouched) { // FIX: Replaced auto-tracking with mouse drag polling
                    camera.translate(
                        -Gdx.input.deltaX * (camera.viewportWidth / Gdx.graphics.width) * 10f,
                        Gdx.input.deltaY * (camera.viewportHeight / Gdx.graphics.height) * 10f
                    ) // FIX: Translate camera by mouse delta scaled to world units
                    camera.update() // FIX: Update camera to reflect manual translation
                }
            }
        } else {
            // Once playback is complete, immediately dispose and trigger the next generation calculation
            for (sim in playbackSimulators) sim.dispose()
            playbackSimulators.clear()
            startEvolutionCycle()
            return
        }

        Gdx.gl.glEnable(GL20.GL_BLEND) // FIX: Enabled OpenGL blending to allow for transparent sensors
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA) // FIX: Set standard alpha blend function

        shapeRenderer.projectionMatrix =
            camera.combined // FIX: Align ShapeRenderer to the world camera to draw sensors in physics space
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled) // FIX: Begin batching filled shapes instead of lines

        shapeRenderer.color = Color.DARK_GRAY // FIX: Set color for the physical ground
        for (sim in playbackSimulators) { // FIX: Dynamically render non-creature objects instead of hardcoding the ground
            sim.world.getBodies(tmpBodies) // FIX: Retrieve all bodies from the current simulation world
            for (body in tmpBodies) { // FIX: Iterate over the retrieved bodies
                if (!sim.creature.bodies.contains(body)) { // FIX: Check if the body belongs to the environment rather than the creature
                    for (fixture in body.fixtureList) { // FIX: Iterate over the fixtures of the environmental body
                        val shape = fixture.shape // FIX: Get the underlying shape
                        if (shape is com.badlogic.gdx.physics.box2d.PolygonShape && shape.vertexCount == 4) { // FIX: Support dynamic rendering for four-sided polygons like the ground
                            shape.getVertex(0, v1) // FIX: Extract local vertex 0
                            shape.getVertex(1, v2) // FIX: Extract local vertex 1
                            shape.getVertex(2, v3) // FIX: Extract local vertex 2
                            shape.getVertex(3, v4) // FIX: Extract local vertex 3
                            v1.set(body.getWorldPoint(v1)) // FIX: Convert local vertex 0 to world space
                            v2.set(body.getWorldPoint(v2)) // FIX: Convert local vertex 1 to world space
                            v3.set(body.getWorldPoint(v3)) // FIX: Convert local vertex 2 to world space
                            v4.set(body.getWorldPoint(v4)) // FIX: Convert local vertex 3 to world space
                            shapeRenderer.triangle(
                                v1.x,
                                v1.y,
                                v2.x,
                                v2.y,
                                v3.x,
                                v3.y
                            ) // FIX: Draw the first triangle of the quad
                            shapeRenderer.triangle(
                                v1.x,
                                v1.y,
                                v3.x,
                                v3.y,
                                v4.x,
                                v4.y
                            ) // FIX: Draw the second triangle of the quad
                        } else if (shape is com.badlogic.gdx.physics.box2d.CircleShape) { // FIX: Support dynamic rendering for circular environmental objects
                            val worldCenter =
                                body.getWorldPoint(shape.position) // FIX: Get the circle's position in world space
                            shapeRenderer.circle(
                                worldCenter.x,
                                worldCenter.y,
                                shape.radius,
                                16
                            ) // FIX: Render the circle dynamically
                        }
                    }
                }
            }
        }

        shapeRenderer.color = Color(1f, 0f, 0f, 0.25f) // FIX: Make sensors render in red with 75% transparency
        for (sim in playbackSimulators) { // FIX: Iterate through all creatures currently being rendered
            for (i in 0 until sim.creature.genome.anatomy.nodes.size) { // FIX: Loop over the anatomy nodes to find their respective sensors
                val node = sim.creature.genome.anatomy.nodes[i]
                val body = sim.creature.bodies[i]
                for (sensor in node.sensors) { // FIX: Draw a line segment outwards from the body position for every sensor using its rotation and range
                    shapeRenderer.circle(
                        body.position.x,
                        body.position.y,
                        sensor.range,
                        36
                    ) // FIX: Render sensors as filled transparent circles
                }
            }
        }

        shapeRenderer.color = Color.WHITE // FIX: Set color for structural edges
        for (sim in playbackSimulators) { // FIX: Iterate through all creatures to draw their edges
            for (edge in sim.creature.edges) { // FIX: Loop over the simulated creature's edges
                val bodyA = sim.creature.nodeMap[edge.nodeA]!! // FIX: Retrieve Box2D body for the start node
                val bodyB = sim.creature.nodeMap[edge.nodeB]!! // FIX: Retrieve Box2D body for the end node
                shapeRenderer.rectLine(
                    bodyA.position.x,
                    bodyA.position.y,
                    bodyB.position.x,
                    bodyB.position.y,
                    0.1f
                ) // FIX: Draw edges as thick solid lines
            }
        }

        shapeRenderer.color = Color.WHITE // FIX: Set color for solid anatomical nodes
        for (sim in playbackSimulators) { // FIX: Iterate through all creatures to draw their bodies
            for (i in 0 until sim.creature.bodies.size) { // FIX: Loop over the anatomy bodies
                val body = sim.creature.bodies[i] // FIX: Fetch the Box2D body
                val mass = sim.creature.genome.anatomy.nodes[i].mass // FIX: Fetch node mass
                shapeRenderer.circle(
                    body.position.x,
                    body.position.y,
                    kotlin.math.sqrt(mass) * 0.4f,
                    16
                ) // FIX: Draw anatomical nodes as solid circles
            }
        }

        shapeRenderer.end() // FIX: Flush the batch to the GPU

        Gdx.gl.glDisable(GL20.GL_BLEND) // FIX: Disable blending to not interfere with text rendering

        batch.projectionMatrix = hudCamera.combined
        batch.begin()
        font.draw(
            batch,
            "Generation: ${generation - 1} | Playback Frame: $playbackTick / $simulationTicks",
            20f,
            Gdx.graphics.height - 20f
        )
        font.draw(
            batch,
            "[Playback: ${if (watchPlayback) "ON" else "OFF"}] Press SPACE to toggle",
            20f,
            Gdx.graphics.height - 40f
        ) // FIX: Removed click instruction to match updated toggle logic
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        debugRenderer.dispose()
        shapeRenderer.dispose() // FIX: Free native memory for the shape renderer
        executor.shutdown()
        for (sim in playbackSimulators) sim.dispose()
    }
}
