package projectiles;

import util.Util;

public class Projectile {
	public int health;
	
	protected double x, y;
	protected double speedX;

	protected double speedY;
	
	protected final double originX, originY;
		
	ProjectileAttributes attributes;
	
	//cunstructors n stuff
	public Projectile(double x, double y, double angle, ProjectileAttributes attributes){
		this.originX = x;
		this.originY = y;
		
		this.x = x;
		this.y = y;
		this.speedX = attributes.speed * Math.cos(angle);
		this.speedY = attributes.speed * Math.sin(angle);
		this.attributes = attributes;
		this.health = this.attributes.damage;
	}

	public void update() {
		this.x += this.speedX;
		this.y += this.speedY;
	}
	
	public boolean isOutOfRange() {
		return Util.dist(x, y, originX, originY) >= this.attributes.range;
	}

	//getters n stuff
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public ProjectileAttributes getAttributes() {
		return this.attributes;
	}
	
	public String toString() {
		return "projectile at " + x + " " + y;
	}
	
	
}
