package levels;

import enemies.Enemy;
import main.GameState;
import projectiles.Turret;

public class Level {
	static GameState gameState;
	
	private int counter = 0;
	
	public final int pointsUntilNextLevel;
	
	public final int enemyHealth;
	public final double enemyRadius;
	public final double enemySpeed;
	public final int spawnInterval;
	
	private Turret[] turrets;
	
	public Level(int pointsUntilNextLevel, int enemyHealth, double enemyRadius, double enemySpeed, int spawnInterval, Turret[] turrets){
		this.pointsUntilNextLevel = pointsUntilNextLevel;
		
		this.enemyHealth = enemyHealth;
		this.enemyRadius = enemyRadius;
		this.enemySpeed = enemySpeed;
		this.spawnInterval = spawnInterval;
		
		this.turrets = turrets;
	}
	
	public static void linkGameState(GameState gs) {
		gameState = gs;
	}
	
	public void initialize() {
		gameState.setTurrets(turrets);
		gameState.setPointsUntilNextLevel(pointsUntilNextLevel);
	}
	
	public void addEnemies() {
		counter++;
		
		if(counter >= spawnInterval) {
			spawnEnemy(enemyHealth, enemyRadius, enemySpeed);
			
			counter = 0;
		}
	}
	
	private void spawnEnemy(int health, double radius, double speed) {
		Enemy enemy = new Enemy(health, radius);
		
		enemy.randomizeDilation(1);
		enemy.levelSpawnedIn = gameState.getLevel();
		
		if(Math.random() < 0.5) {
			enemy.setSpeed(speed);
			enemy.setOrigin(-GameState.GRID_WIDTH - enemy.getRadius(), Math.random() * GameState.GRID_HEIGHT * 2 - GameState.GRID_HEIGHT);
		}else {
			enemy.setSpeed(-speed);
			enemy.setOrigin(GameState.GRID_WIDTH + enemy.getRadius(), Math.random() * GameState.GRID_HEIGHT * 2 - GameState.GRID_HEIGHT);
		}
		
		gameState.addEnemy(enemy);
	}

}
