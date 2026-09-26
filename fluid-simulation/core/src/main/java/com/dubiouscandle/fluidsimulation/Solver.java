package com.dubiouscandle.fluidsimulation;

import com.badlogic.gdx.utils.Array;

public class Solver {
	private final float worldWidth, worldHeight;
	
	public float gravity = -9.8f;
	public Array<Particle> particles = new Array<>();
	private PartitioningGrid partitioningGrid = new PartitioningGrid(1, worldWidth, worldHeight);

	public Solver(float worldWidth, float worldHeight) {
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
	}

	public void step(float delta) {
		for (int i = 0; i < particles.size; i++) {
			Particle p = particles.items[i];

			partitioningGrid.forEachAdjacent((a, b) -> {
				float 
			});
		}

		for (Particle p : particles) {
			p.x += p.vx * delta;
			p.y += p.vy * delta;
		}
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}
}
