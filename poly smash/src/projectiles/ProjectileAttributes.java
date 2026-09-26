package projectiles;

import java.awt.Color;

public class ProjectileAttributes {	
	public final int damage;
	public final double speed, range, radius;
	public final Color color;
	public static final Color DEFAULT_COLOR = Color.decode("#ed9df2");
		
	//packed constructor
	public ProjectileAttributes(int damage, double speed, double radius, double range, Color color){
		this.damage = damage;
		this.speed = speed;
		this.radius = radius;
		this.range = range;
		this.color = color;
	}
	
	//same as packed but with a default color
	public ProjectileAttributes(int damage, double speed, double radius, double range){
		this(damage, speed, radius, range, DEFAULT_COLOR);
	}
	
	public Projectile generateNewProjectile(double x, double y, double angle) {
		return new Projectile(x, y, angle, this);
	}
	
}
