package com.gliucode.evolution;

public class Particle {
	public float x, y;
	public float px, py;
	public float fx, fy;
	public float invMass = 1;

	public Particle() {
	}

	public void step(float delta) {
		float x1 = 2 * x - px + fy * delta * delta * invMass;
		float y1 = 2 * y - py + fx * delta * delta * invMass;

		px = x;
		py = y;

		x = x1;
		y = y1;
	}
}
