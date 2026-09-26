package projectiles;

public class TrapProjectileAttributes extends ProjectileAttributes {
	public TrapProjectileAttributes(int damage, double speed, double radius, double range) {
		super(damage, speed, radius, range);
	}
	public Projectile generateNewProjectile(double x, double y, double angle) {
		return new TrapProjectile(x, y, angle, this);
	}


}
