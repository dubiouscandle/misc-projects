package verlet;

import java.util.ArrayList;

public class Solver {
	public static final float RADIUS = 0.005f;
	public static final int NUM_CELLS = (int) (1 / (2 * RADIUS));
	public static final float CELL_SIZE = 1f / NUM_CELLS;
	public static final float DELTA_T = 1 / 600f;
	public static final int MAX_PARTICLES_PER_CELL = 30;
	public static final float GRAVITY = -1f;

	public ArrayList<Particle> particles = new ArrayList<>();
	private Particle[][] buckets = new Particle[NUM_CELLS * NUM_CELLS][MAX_PARTICLES_PER_CELL];

	public Solver() {
	}

	public void createParticle(float x, float y) {
		Particle particle = new Particle(x, y);
		particles.add(particle);
	}

	public void step() {
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < MAX_PARTICLES_PER_CELL; j++) {
				buckets[i][j] = null;
			}
		}

		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);

			float nx = 2 * particle.x - particle.px;
			float ny = 2 * particle.y - particle.py + GRAVITY * DELTA_T * DELTA_T;

			if (nx > 1 - RADIUS)
				nx = 1 - RADIUS;
			else if (nx < RADIUS)
				nx = RADIUS;
			if (ny > 1 - RADIUS)
				ny = 1 - RADIUS;
			else if (ny < RADIUS)
				ny = RADIUS;

			particle.px = particle.x;
			particle.py = particle.y;
			particle.x = nx;
			particle.y = ny;

			int gx = (int) (nx / CELL_SIZE);
			int gy = (int) (ny / CELL_SIZE);

			if (gx >= NUM_CELLS) {
				gx = NUM_CELLS - 1;
			}
			if (gy >= NUM_CELLS) {
				gy = NUM_CELLS - 1;
			}

			Particle[] bucket = buckets[gx + gy * NUM_CELLS];
			for (int j = 0; j < MAX_PARTICLES_PER_CELL; j++) {
				if (bucket[j] == null) {
					bucket[j] = particle;
					break;
				}
			}
		}

		for (int y = 0; y < NUM_CELLS; y++) {
			for (int x = 0; x < NUM_CELLS; x++) {
				Particle[] curBucket = buckets[x + NUM_CELLS * y];

				if (y != NUM_CELLS - 1) {
					Particle[] adjBucket = buckets[x + NUM_CELLS * (y + 1)];
					for (int i = 0; i < adjBucket.length && adjBucket[i] != null; i++) {
						for (int j = 0; j < curBucket.length && curBucket[j] != null; j++) {
							handleCollision(adjBucket[i], curBucket[j]);
						}
					}
				}
				if (x != NUM_CELLS - 1) {
					Particle[] adjBucket = buckets[x + 1 + NUM_CELLS * y];
					for (int i = 0; i < adjBucket.length && adjBucket[i] != null; i++) {
						for (int j = 0; j < curBucket.length && curBucket[j] != null; j++) {
							handleCollision(adjBucket[i], curBucket[j]);
						}
					}
				}
				if (x != NUM_CELLS - 1 && y != NUM_CELLS - 1) {
					Particle[] adjBucket = buckets[x + 1 + NUM_CELLS * (y + 1)];
					for (int i = 0; i < adjBucket.length && adjBucket[i] != null; i++) {
						for (int j = 0; j < curBucket.length && curBucket[j] != null; j++) {
							handleCollision(adjBucket[i], curBucket[j]);
						}
					}
				}
				if (x != NUM_CELLS - 1 && y != 0) {
					Particle[] adjBucket = buckets[x + 1 + NUM_CELLS * (y - 1)];
					for (int i = 0; i < adjBucket.length && adjBucket[i] != null; i++) {
						for (int j = 0; j < curBucket.length && curBucket[j] != null; j++) {
							handleCollision(adjBucket[i], curBucket[j]);
						}
					}
				}
				for (int i = 0; i < curBucket.length && curBucket[i] != null; i++) {
					for (int j = i + 1; j < curBucket.length && curBucket[j] != null; j++) {
						handleCollision(curBucket[i], curBucket[j]);
					}
				}

			}
		}
	}

	private void handleCollision(Particle p1, Particle p2) {
		float dx = p1.x - p2.x;
		float dy = p1.y - p2.y;
		float distSq = dx * dx + dy * dy;
		float minDist = RADIUS + RADIUS;
		float minDistSq = minDist * minDist;

		if (distSq < minDistSq && distSq > 0.000001f) {
			float dist = (float) Math.sqrt(distSq);
			float overlap = 0.5f * (minDist - dist);

			float nx = dx / dist;
			float ny = dy / dist;

			p1.x += nx * overlap;
			p1.y += ny * overlap;

			p2.x -= nx * overlap;
			p2.y -= ny * overlap;
		}
	}
}
