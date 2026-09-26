package particles;

import java.awt.Color;

import util.Util;

public class Particle {//maybe could have extended projectile but they are kind of different things so i didnt
	private double x, y;
	private double speedX, speedY, range;
	
	private final double originX, originY;
	public final double radius;
	
	public final Color color;
	
	public void update() {
		this.x += this.speedX;
		this.y += this.speedY;
	}
	
	public boolean isOutOfRange() {
		return Util.dist(x, y, originX, originY) >= this.range;
	}
	
	public Particle(double x, double y, double speed, double range, double radius, Color color) {
		this.x = x;
		this.y = y;
		
		this.range = range;
		this.radius = radius;
		
		originX = x;
		originY = y;
		
		double angle = Math.random() * 2 * Math.PI;
		
		this.speedX = Math.cos(angle) * speed;
		this.speedY = Math.sin(angle) * speed;
		
		this.color = color;
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
}
