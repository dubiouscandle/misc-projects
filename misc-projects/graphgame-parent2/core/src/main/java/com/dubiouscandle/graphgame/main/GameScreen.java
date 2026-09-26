package com.dubiouscandle.graphgame.main;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dubiouscandle.graphgame.components.BodyComponent;
import com.dubiouscandle.graphgame.components.ContactComponent;
import com.dubiouscandle.graphgame.components.ExplosionComponent;
import com.dubiouscandle.graphgame.components.TransformComponent;
import com.dubiouscandle.graphgame.systems.ContactSystem;
import com.dubiouscandle.graphgame.systems.ExplosionSystem;
import com.dubiouscandle.graphgame.systems.PhysicsSystem;
import com.dubiouscandle.graphgame.systems.RemoveSystem;

public class GameScreen implements Screen {
	private Main main;
	private Engine engine;
	private FitViewport worldViewport;
	private World world;

	private Box2DDebugRenderer dr = new Box2DDebugRenderer();

	private SpriteBatch batch = new SpriteBatch();

	public GameScreen(Main main) {
		worldViewport = new FitViewport(160, 90, new OrthographicCamera(160, 90));

		engine = new Engine();
		world = new World(Vector2.Zero, false);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				BodyDef bf = new BodyDef();
				bf.type = BodyDef.BodyType.DynamicBody;
				Body b = world.createBody(bf);
				CircleShape circleShape = new CircleShape();
				circleShape.setRadius(3);
				b.createFixture(circleShape, 1);
				b.setTransform(i * 10, j * 10, 3);
				
				Entity entity = new Entity();
				entity.add(new BodyComponent(b));
				b.setUserData(entity);
//				entity.add(new ContactComponent());

				engine.addEntity(entity);
			}
		}

		{
			BodyDef bf = new BodyDef();
			bf.type = BodyDef.BodyType.DynamicBody;
			Body b = world.createBody(bf);
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(3);
			b.createFixture(circleShape, 1);
			b.setTransform(-40, 0, 0);
			b.setLinearVelocity(10, 0);

			Entity entity = new Entity();
			entity.add(new BodyComponent(b));
			entity.add(new ContactComponent());
			entity.add(new TransformComponent());
			ExplosionComponent ec = new ExplosionComponent(30, 2000, 64);
			entity.add(ec);
			b.setUserData(entity);
			engine.addEntity(entity);
		}

		world.setContactListener(new PhysicsContactListener());

		engine.addSystem(new ExplosionSystem(world));
		engine.addSystem(new ContactSystem());
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new RemoveSystem());

		this.main = main;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(delta);

		dr.render(world, worldViewport.getCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		worldViewport.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		dr.dispose();
		world.dispose();
	}
}
