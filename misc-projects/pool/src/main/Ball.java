package main;

import java.awt.Color;

public class Ball {
	public static final double DEFAULT_BALL_RADIUS = 61.5 / 10 / 1.5;// 61.5mm * cm/10mm /2(for radius)
	private static final double DEFAULT_BALL_RESTITUTION = 0.89;
	private static final double DEFAULT_MASS = 1;// doesnt really matter that much but whatever

	private double radius, x, y, xVelocity, yVelocity, restitution = DEFAULT_BALL_RESTITUTION, mass = DEFAULT_MASS;
	private final int number;
	private final Color color;

	private Texture texture = Texture.SOLID;

	private static final Color[] COLORS = { Color.YELLOW, Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE,
			Color.GREEN, Color.decode("#583927") };

	public Ball(int number) {
		this.number = number;

		if (number <= 8)
			this.color = COLORS[number];
		else if (number - 8 < COLORS.length)
			this.color = COLORS[number - 8];
		else
			this.color = Color.getHSBColor((float) Math.random(), 1, 1);

		if (number > 8)
			texture = Texture.STRIPED;
		else
			texture = Texture.SOLID;

		if (number == 0)
			this.texture = Texture.CUE_BALL;
		else if (number == 8)
			this.texture = Texture.EIGHT_BALL;

		this.radius = DEFAULT_BALL_RADIUS;
	}

	public Ball(int number, Texture texture) {
		this.number = number;

		if (texture == Texture.CUE_BALL)

			this.color = Color.WHITE;
		else if (texture == Texture.EIGHT_BALL)
			this.color = Color.BLACK;
		else
			this.color = COLORS[number % 7];

		this.texture = texture;

		this.radius = DEFAULT_BALL_RADIUS;
	}

	public Color getColor() {
		return color;
	}

	public Texture getTexture() {
		return texture;
	}

	public int getNumber() {
		return number;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public double getRadius() {
		return radius;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void update(double timeSeconds) {
		x += xVelocity * timeSeconds;
		y += yVelocity * timeSeconds;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getRestution() {
		return this.restitution;
	}

	public boolean isCollidingWith(Ball ball2) {
		double dx = this.x - ball2.x;
		double dy = this.y - ball2.y;
		double sumOfRadii = this.radius + ball2.radius;

		return dx * dx + dy * dy <= sumOfRadii * sumOfRadii;
	}

	public double getMass() {
		return mass;
	}

	public void handleCollision(Ball other) {
		double dx = other.getX() - this.getX();
		double dy = other.getY() - this.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);

		if (distance == 0) {
			return; // Prevent division by zero
		}

		double overlap = this.getRadius() + other.getRadius() - distance;
		if (overlap > 0) {
			// Adjust positions to resolve overlap
			double offsetX = (dx / distance) * overlap / 2;
			double offsetY = (dy / distance) * overlap / 2;
			this.setX(this.getX() - offsetX);
			this.setY(this.getY() - offsetY);
			other.setX(other.getX() + offsetX);
			other.setY(other.getY() + offsetY);
		}

		// Calculate the new velocities after collision
		double vx1 = this.getxVelocity();
		double vy1 = this.getyVelocity();
		double vx2 = other.getxVelocity();
		double vy2 = other.getyVelocity();

		double mass1 = this.getMass();
		double mass2 = other.getMass();

		double normalX = dx / distance;
		double normalY = dy / distance;

		double relativeVelocityX = vx2 - vx1;
		double relativeVelocityY = vy2 - vy1;

		double dotProduct = normalX * relativeVelocityX + normalY * relativeVelocityY;

		double coefficientOfRestitution = Math.min(this.getRestution(), other.getRestution());

		double impulse = (2 * dotProduct) / (mass1 + mass2);
		this.setxVelocity(vx1 + impulse * mass2 * normalX * coefficientOfRestitution);
		this.setyVelocity(vy1 + impulse * mass2 * normalY * coefficientOfRestitution);
		other.setxVelocity(vx2 - impulse * mass1 * normalX * coefficientOfRestitution);
		other.setyVelocity(vy2 - impulse * mass1 * normalY * coefficientOfRestitution);
	}

	public double getSpeed() {
		return Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
	}

	public double getSpeedSquared() {
		return xVelocity * xVelocity + yVelocity * yVelocity;
	}

	public void setVelocity(double xVelocity, double yVelocity) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}
