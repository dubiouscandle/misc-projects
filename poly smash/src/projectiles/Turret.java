package projectiles;

import main.GameState;

public class Turret {
	static GameState gameState;
	int counter;
		
	protected final ProjectileAttributes projectileAttributes;	
	protected final double angle;	
	private final int fireInterval;
	private final int offset;
	
	//constructor
	public Turret(int fireInterval, int offset, double angle, ProjectileAttributes projectileAttributes) {
		this.fireInterval = fireInterval;
		this.offset = offset;
		counter = 0;
		this.angle = angle;
		this.projectileAttributes = projectileAttributes;
	}
	
	//setter
	public static void linkGameState(GameState gs) {
		gameState = gs;
	}
	
	public void update() {
		counter++;
		if((counter + offset) % fireInterval == 0) {
			this.shoot(gameState.getPlayer().getX(), gameState.getPlayer().getY());
		}
	};
	
	public void shoot(double x, double y) {
		gameState.addProjectile(projectileAttributes.generateNewProjectile(x, y, angle));
	};
	
	public ProjectileAttributes getProjectileAttributes() {
		return projectileAttributes;
	}
	
	public double getAngle() {
		return angle;
	}
}
