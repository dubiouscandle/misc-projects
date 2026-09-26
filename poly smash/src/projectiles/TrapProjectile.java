package projectiles;

import util.Util;

public class TrapProjectile extends Projectile{

	public TrapProjectile(double x, double y, double angle, ProjectileAttributes attributes) {
		super(x, y, angle, attributes);
	}
		
	@Override
	public void update() {
		double distance = Math.max(1, Util.dist(x, y, originX, originY));
		double factor = 1/Math.exp(distance/1.3);
		
		this.x += this.speedX * factor;
		this.y += this.speedY * factor;
	}
}


