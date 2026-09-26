package com.dubiouscandle.graphgame;

public class Node {
	public float x;
	public float y;
	private float px;
	private float py;
	private float fx;
	private float fy;
	private float radius;
	private float invMass;
	private float charge;

	public Node(float x, float y, float charge) {
		super();
		this.x = x;
		this.y = y;
		px = x;
		py = y;
	}

	public Node(float charge) {
		this(0, 0, charge);
	}

	public void applyForce(float fx, float fy) {
		this.fx += fx;
		this.fy += fy;
	}

	public void step(float delta) {
		float nx = 2 * x - px + fx * delta * delta * invMass;
		float ny = 2 * y - py + fy * delta * delta * invMass;

		px = x;
		py = y;

		x = nx;
		y = ny;
	}
}
