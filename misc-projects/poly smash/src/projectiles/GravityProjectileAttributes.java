package projectiles;

public class GravityProjectileAttributes extends ProjectileAttributes{//i didnt know how else to make it so it could spawn a gravity projectile without using like if statements or something else goofy

	public GravityProjectileAttributes(int damage, double speed, double radius, double range) {
		super(damage, speed, radius, range);
	}
	
	public Projectile generateNewProjectile(double x, double y, double angle) {
		return new GravityProjectile(x, y, angle, this);
	}
	
}
