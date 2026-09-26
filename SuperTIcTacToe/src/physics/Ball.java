package physics;

import java.awt.Color;

public class Ball {
	public static final double DEFAULT_RADIUS = 10;
	public static final double DEFAULT_RESTITUTION = 0.9;

	private double radius = DEFAULT_RADIUS, restitution = DEFAULT_RESTITUTION;
	protected double x, y;
	protected double vx, vy;

	public Color color = Color.BLACK;

	public void handleCollision(Ball other) {
		double dx = other.x - this.x;
		double dy = other.y - this.y;

		double distanceSquared = dx * dx + dy * dy;

		double radiusSum = radius + other.radius;

		if (distanceSquared > radiusSum * radiusSum || distanceSquared == 0)
			return;

		double distance = Math.sqrt(distanceSquared);

		double overlap = radiusSum - distance;

		if (overlap > 0) {
			double sx = (dx / distance) * overlap * 0.5;
			double sy = (dy / distance) * overlap * 0.5;

			this.x -= sx;
			this.y -= sy;
			other.x += sx;
			other.y += sy;
		}

		double nx = dx / distance;
		double ny = dy / distance;

		double rvx = other.vx - this.vx;
		double rvy = other.vy - this.vy;

		double impulse = (nx * rvx + ny * rvy) * restitution * other.restitution;

		double ix = impulse * nx;
		double iy = impulse * ny;

		this.vx += ix;
		this.vy += iy;
		other.vx -= ix;
		other.vy -= iy;
	}

	public Ball(double x, double y) {
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
	}

	public Ball(double x, double y, double vx, double vy, Color color) {
		this(x, y);

		this.vx = vx;
		this.vy = vy;
		this.color = color;
	}

}
