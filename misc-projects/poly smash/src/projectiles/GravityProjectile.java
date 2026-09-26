package projectiles;

public class GravityProjectile extends Projectile{

	private static final double GRAVITY_CONSTANT = 0.01;
	
	public GravityProjectile(double x, double y, double angle, ProjectileAttributes attributes) {
		super(x, y, angle, attributes);
	}
	
	//it fals now
	@Override
	public void update() {
		
		//this is kind of like a riemann sum where the integral of the speedY is this.y + (c or originY)
		//i think one increment per frame is good enough because each frame happens 60 times per second so it is probably good enough for a simple game yayayaya
		this.speedY -= GRAVITY_CONSTANT;
		
		this.x += this.speedX;
		this.y += this.speedY;
	}
	
	@Override//apparently you have to put @overide for some rasein
	public boolean isOutOfRange() {
		return originY-y >= this.attributes.range;
	}
}
