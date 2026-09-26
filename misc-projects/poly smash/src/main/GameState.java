package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enemies.*;
import levels.*;
import particles.*;
import projectiles.*;
import util.*;

public class GameState {	
	public static final Color PLAYER_EXPLOSION_COLOR = Color.decode("#ed9df2");
	
	private double mouseX;
	private double mouseY;
	private boolean mousePressed = false;
	public boolean gameEnded = true;
	public boolean playerWon = false;
	public boolean cheated = false;
	public int deaths = 0;
			
	//variables which are what store the 'progression' of the player kind of
	private Player player = new Player();		
	private int level = -1;
	private int pointsUntilNextLevel;
	
	private ArrayList<Turret> turrets = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Particle> particles = new ArrayList<>();
	
	private final Level[] levels = {
			//	public Level(int pointsUntilNextLevel, int enemyHealth, double enemyRadius, double enemySpeed, int spawnInterval, Turret[] turrets){
			
		    new Level(3, 1, 0.5, 0.05, 16, new Turret[] {
		        // Average
		        new Turret(30, 0, 0, new ProjectileAttributes(1, 0.2, 0.26, 4))
		    }),
		    
		    new Level(3, 1, 0.5, 0.06, 14, new Turret[] {
		        // Flanker
		        new Turret(30, 0, 0, new ProjectileAttributes(1, 0.2, 0.26, 4)),
		        new Turret(30, 0, Math.PI, new ProjectileAttributes(1, 0.2, 0.26, 4))
		    }),
		    
		    new Level(3, 1, 0.5, 0.07, 12, new Turret[] {
		        // Sniper
		        new Turret(35, 0, 0, new ProjectileAttributes(3, 0.3, 0.26, 12))
		    }),
		    
		    new Level(4, 2, 0.5, 0.08, 11, new Turret[] {
		        // Quadruple Guy
		        new Turret(33, 0, 0, new ProjectileAttributes(2, 0.2, 0.26, 4)),
		        new Turret(33, 0, Math.PI/2, new ProjectileAttributes(2, 0.2, 0.26, 4)),
		        new Turret(33, 0, Math.PI, new ProjectileAttributes(2, 0.2, 0.26, 4)),
		        new Turret(33, 0, Math.PI * 3/2, new ProjectileAttributes(2, 0.2, 0.26, 4))
		    }),
		    
		    //big boi
		    new Level(4, 4, 0.5, 0.09, 16, createGiantCannonBall()),
		    
		    new Level(4, 4, 0.5, 0.09, 14, new Turret[] {
		        // Destructonatoreeeeeeeeeeeeeeee
			        new Turret(40, 0, 0, new ProjectileAttributes(12, 0.04, 0.1, 4)),
			        new Turret(40, 0, 0, new ProjectileAttributes(12, 0.08, 0.1, 8)),
			        new Turret(40, 0, 0, new ProjectileAttributes(12, 0.12, 0.1, 16)),
			        new Turret(40, 0, 0, new ProjectileAttributes(12, 0.16, 0.5, 20)),
		    }),
		    new Level(5, 3, 0.5, 0.09, 12, new Turret[] {
		        //machine Gunner
		        new Turret(8, 0, 0, new ProjectileAttributes(1, 0.2, 0.26, 9))
		    }),
		    
		    
		    //shotgunner
		    new Level(5, 4, 0.5, 0.09, 12, createShotGunner()),
		    
		    //wierdness
		    new Level(5, 3, 0.5, 0.09, 12, createWeirdnessTurrets()),
		    
		    //trapper
		    new Level(5, 4, 0.5, 0.1, 12, new Turret[] {
			        new Turret(16, 0, 0, new TrapProjectileAttributes(2, 1, 0.26, 3)),
			        new Turret(16, 0, Math.PI * 2/3, new TrapProjectileAttributes(2, 1, 0.26, 3)),
			        new Turret(16, 0, Math.PI * 4/3, new TrapProjectileAttributes(2, 1, 0.26, 3)),
			        
			        new Turret(16, 8, +Math.PI/3, new TrapProjectileAttributes(2, 1, 0.26, 3)),
			        new Turret(16, 8, Math.PI * 2/3+Math.PI/3, new TrapProjectileAttributes(2, 1, 0.26, 3)),
			        new Turret(16, 8, Math.PI * 4/3+Math.PI/3, new TrapProjectileAttributes(2, 1, 0.26, 3)),
		    }),
		    
		    //octo guy
		    new Level(6, 4, 0.5, 0.1, 11, createOctoGuyTurrets()),

		    new Level(6, 4, 0.5, 0.1, 11, new Turret[] {
		        // tripple shooter
			        new Turret(22, 0, 0, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 4, 0, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 8, 0, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 0, Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 4, Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 8, Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 0, -Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 4, -Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
			        new Turret(22, 8, -Math.PI/6, new ProjectileAttributes(1, 0.2, 0.26, 15)),
		    }),
		    		    
		    new Level(7, 6, 0.5, 0.1, 10, new Turret[] {
			        // SUPER DESCTURORHRHAWIRUHIUOSBFIUOUUUUR
			        new Turret(120, 0, 0, new ProjectileAttributes(1234567890, 0.15, 4, 20))
			    }),

		    new Level(8, 4, 0.5, 0.1, 9, createLancerTurrets()),
		    new Level(9, 5, 0.5, 0.1, 9, createSpinny()),
		    new Level(9, 5, 0.5, 0.1, 9, createSquareTurrets()),
		};
	
	// methods for special loop turrets because you cant put those inside as parametersasdf
	private Turret[] createGiantCannonBall() {
		final int amount = 12;
		
		Turret[] turret = new Turret[amount];
		
		for(int i = 0; i < amount; i++) {
			double angle = 2.0 * i/amount * Math.PI;
			
			double x = Math.cos(angle)+2;
			double y = Math.sin(angle)+1;
			
			turret[i] = new Turret(50, 0, Math.atan2(y, x), new GravityProjectileAttributes(1, Math.hypot(x, y)/5, 0.2, 10));
		}
		
		return turret;

	}
	private Turret[] createSpinny() {
	    Turret[] turrets = new Turret[16];
	    for (int i = 0; i < 16; i++) {
	        turrets[i] = new Turret(24, 6*i, i/16.0 * Math.PI * 2, new ProjectileAttributes(1, 0.2, 0.26, 7));
	    }
	    return turrets;
	}
	private Turret[] createShotGunner() {
		final int amount = 24;
		final double arc = Math.PI / 2;
		
	    Turret[] turrets = new Turret[amount];
	    for (int i = 0; i < amount; i++) {
	        turrets[i] = new Turret(40, 0, arc * i/amount - arc/2, new ProjectileAttributes(1, 0.1 + Math.random() * 0.1, 0.13, 7));
	    }
	    return turrets;
	}
	private Turret[] createOctoGuyTurrets() {
	    Turret[] turrets = new Turret[8];
	    for (int i = 0; i < 8; i++) {
	        turrets[i] = new Turret(20, 20 * i / 2, i * Math.PI / 4, new ProjectileAttributes(1, 0.2, 0.26, 7));
	    }
	    return turrets;
	}
	private Turret[] createWeirdnessTurrets() {
	    return new Turret[] {
	        new Turret(60, 0, 0, new ProjectileAttributes(4, 0.15, 0.4, 12)),
	        new Turret(60, 10, 0, new ProjectileAttributes(3, 0.15, 0.3, 12)),
	        new Turret(60, 20, 0, new ProjectileAttributes(2, 0.15, 0.2, 12)),
	        new Turret(60, 30, 0, new ProjectileAttributes(1, 0.15, 0.1, 12)),
	        new Turret(60, 40, 0, new ProjectileAttributes(2, 0.15, 0.2, 12)),
	        new Turret(60, 50, 0, new ProjectileAttributes(3, 0.15, 0.3, 12)),
	        
	        new Turret(60, 0, Math.PI, new ProjectileAttributes(4, 0.15, 0.4, 12)),
	        new Turret(60, 10, Math.PI, new ProjectileAttributes(3, 0.15, 0.3, 12)),
	        new Turret(60, 20, Math.PI, new ProjectileAttributes(2, 0.15, 0.2, 12)),
	        new Turret(60, 30, Math.PI, new ProjectileAttributes(1, 0.15, 0.1, 12)),
	        new Turret(60, 40, Math.PI, new ProjectileAttributes(2, 0.15, 0.2, 12)),
	        new Turret(60, 50, Math.PI, new ProjectileAttributes(3, 0.15, 0.3, 12))
	    };
	}
	private Turret[] createLancerTurrets() {
	    List<Turret> turrets = new ArrayList<>();
	    for (double i = 0; i < 2; i += 0.2) {
	        double x = i;
	        double y = -x / 2 + 1;
	        double angle = Math.atan2(y, x);
	        double speed = Math.hypot(x, y) * 0.2;
	        turrets.add(new Turret(70, 0, angle, new ProjectileAttributes(2, speed, 0.26, Math.exp(0.01 * speed) * 8)));
	        turrets.add(new Turret(70, 0, -angle, new ProjectileAttributes(2, speed, 0.26, Math.exp(0.01 * speed) * 8)));
	    }
	    return turrets.toArray(new Turret[0]);
	}
	private Turret[] createSquareTurrets() {
	    List<Turret> turrets = new ArrayList<>();
	    for (double i = -2; i < 2; i += 0.5) {
	        double x = i;
	        double y = 2;
	        double angle = Math.atan2(y, x);
	        double speed = Math.hypot(x, y) * 0.08;
	        double range = speed * 35;
	        turrets.add(new Turret(80, 0, angle, new ProjectileAttributes(2, speed, 0.26, range)));
	        turrets.add(new Turret(80, 0, angle + Math.PI / 2, new ProjectileAttributes(2, speed, 0.26, range)));
	        turrets.add(new Turret(80, 0, angle + Math.PI, new ProjectileAttributes(2, speed, 0.26, range)));
	        turrets.add(new Turret(80, 0, angle + Math.PI * 3 / 2, new ProjectileAttributes(2, speed, 0.26, range)));
	    }
	    return turrets.toArray(new Turret[0]);
	}
	
	private final int highestLevel = levels.length-1;
	
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEIGHT = 10;


	//constructor/initializer
	GameState(){
		Turret.linkGameState(this);
		Level.linkGameState(this);
	}

	//where most of the stuff happens
	public void update() {		
		
		//if the player kills the required amount of enemies then go to the next level
		if(pointsUntilNextLevel <= 0) {
			level++;
						
			//also set every enemies health to 1 so theres not like a bajillion enemies and it makes the game eaiiser
			for(Enemy enemy : enemies) {
				if(enemy.levelSpawnedIn <= level - 2) {
					enemy.health = 0;
					addParticleEffect(enemy.getX(), enemy.getY(), 12, 0.1, 0.25, 1, 4, 0.12, 0.2, Color.WHITE);
				}else {
					enemy.health = 1;
				}
			}
			
			if(level <= highestLevel) {
				levels[level].initialize();
			}
		}	
		if(level <= highestLevel) {
			levels[level].addEnemies();
		}
		
		if(!gameEnded) {
			movePlayer();
		}else {
			player.setPosition(-100, -100);
		}
		
		updateEnemies();
		
		updateTurrets();
		
		updateProjectiles();
		
		checkForCollisions();
		
		updateParticles();
		
		if(player.health <= 0) {
			playerWon = false;
			gameEnded = true;
			deaths++;
			addParticleEffect(player.getX(), player.getY(), 50, 0.1, 0.3, 0, 5, 0.1, 0.23, PLAYER_EXPLOSION_COLOR);
		}
		
		if(level > highestLevel) {
			playerWon = true;
			gameEnded = true;
			for(Enemy enemy : enemies) {
				addParticleEffect(enemy.getX(), enemy.getY(), 30, 0.1, 0.25, 1, 4, 0.12, 0.2, enemy.getColor());
			}
			
			enemies.clear();
		}
		
		if(mousePressed && gameEnded) {
			reset();
		}
		
		mousePressed = false;
	}
	
	//consolidation
	private void updateEnemies() {
		ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
		
		for(Enemy enemy : enemies) {									
			if(enemy.health <= 0 || enemy.getX() - enemy.getRadius() > GRID_WIDTH || enemy.getX() + enemy.getRadius() < -GRID_WIDTH) {
				enemiesToRemove.add(enemy);
				if(enemy.health <= 0) {					
					if(!gameEnded && enemy.levelSpawnedIn == level) {
						setPointsUntilNextLevel(getPointsUntilNextLevel() - 1);
					}
				}
			}else {
				enemy.move();
			}
		}
		
		enemies.removeAll(enemiesToRemove);
	}
	private void movePlayer() {
		player.setPosition(Util.constrain(mouseX, -GRID_WIDTH, GRID_WIDTH), Util.constrain(mouseY, -GRID_HEIGHT, GRID_HEIGHT));
	}
	private void updateTurrets() {
		for(Turret turret : turrets) {
			turret.update();
		}
	}
	private void updateParticles() {
		ArrayList<Particle> particlesToRemove = new ArrayList<>();
		
		for(Particle particle : particles) {
			if(particle.isOutOfRange() || particle.getX() - particle.radius > GRID_WIDTH || particle.getX() + particle.radius < -GRID_WIDTH || particle.getY() - particle.radius > GRID_HEIGHT || particle.getY() + particle.radius < -GRID_HEIGHT) {
				particlesToRemove.add(particle);
			}else {
				particle.update();
			}
		}
		
		particles.removeAll(particlesToRemove);
	}
	
	private void updateProjectiles() {
		ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
		
		for(Projectile projectile : projectiles) {
			if(projectile.health <= 0 || projectile.isOutOfRange() || projectile.getX() - projectile.getAttributes().radius > GRID_WIDTH || projectile.getX() + projectile.getAttributes().radius < -GRID_WIDTH || projectile.getY() - projectile.getAttributes().radius > GRID_HEIGHT || projectile.getY() + projectile.getAttributes().radius < -GRID_HEIGHT) {
				projectilesToRemove.add(projectile);
			}else {
				projectile.update();
			}
		}
		
		projectiles.removeAll(projectilesToRemove);
	}
	
	private void checkForCollisions() {	
		ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

		//check for player
		for(Enemy enemy : enemies) {
			if(Util.dist(enemy.getX(), enemy.getY(), player.getX(), player.getY()) < enemy.getRadius() + player.getRadius()){
				enemiesToRemove.add(enemy);
				player.health--;
			}
		}
		
		enemies.removeAll(enemiesToRemove);
				
		//check collisions between projectiles and enemys
		
		ArrayList<Enemy> enemiesToExplode = new ArrayList<>();
		ArrayList<Color> oldEnemyColors = new ArrayList<>();
		
		for(Projectile projectile : projectiles) {
			for(Enemy enemy : enemies) {
				
				if(Util.dist(projectile.getX(), projectile.getY(), enemy.getX(), enemy.getY()) < projectile.getAttributes().radius + enemy.getRadius()) {
					enemiesToExplode.add(enemy);
					oldEnemyColors.add(new Color(enemy.getColor().getRGB()));

					int temp = projectile.health;
					
					projectile.health -= enemy.health;
					enemy.health -= temp;
				}
			}
		}	
		
		for(int i = 0; i < enemiesToExplode.size(); i++) {
			Enemy enemy = enemiesToExplode.get(i);
			
			addParticleEffect(enemy.getX(), enemy.getY(), 12, 0.1, 0.25, 1, 4, 0.12, 0.2, enemy.levelSpawnedIn == level ? enemy.getColor() : Color.WHITE);
		}
	}
	
	//getters
	public int getLevel() {
		return level;
	}
	public ArrayList<Enemy> getEnemies(){
		return this.enemies;
	}
	public Player getPlayer() {
		return this.player;
	}
	public ArrayList<Projectile> getProjectiles() {
		return this.projectiles;
	}
	public ArrayList<Turret> getTurrets(){
		return this.turrets;
	}
	
	//setters
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	public void setTurrets(Turret[] turrets) {
		this.turrets.clear();
		Collections.addAll(this.turrets, turrets);		
	}
	
	//reset the game
	private void reset() {
		player = new Player();		
		
		//set it to a negative number so that the initializer code knows to do it
		level = -1;
		pointsUntilNextLevel = -1;
		
		turrets.clear();
		projectiles.clear();
		enemies.clear();
		
		gameEnded = false;
	}
	
	
	//misc
	private void addParticleEffect(double x, double y, int quantity, double minSpeed, double maxSpeed, double minRange, double maxRange, double minRadius, double maxRadius, Color color) {
		for(int i = 0; i < quantity; i++) {
			particles.add(new Particle(x, y, Math.random() * (maxSpeed-minSpeed) + minSpeed, Math.random() * (maxRange-minRange) + minRange, Math.random() * (maxRadius-minRadius) + minRadius, color));
		}
	}
	public void handleMousePressed(MouseEvent e) {
		mousePressed = true;
	}
	public void handleMouseMovement(MouseEvent e) {
		mouseX = GamePanel.convertToGridX(e.getX());
		mouseY = GamePanel.convertToGridY(e.getY());
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public int getPointsUntilNextLevel() {
		return pointsUntilNextLevel;
	}

	public void setPointsUntilNextLevel(int pointsUntilNextLevel) {
		this.pointsUntilNextLevel = pointsUntilNextLevel;
	}
	public ArrayList<Particle> getParticles() {
		return this.particles;
	}
	
	public void handleKeyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
			pointsUntilNextLevel = -1;
			cheated = true;
		}
	}
}
