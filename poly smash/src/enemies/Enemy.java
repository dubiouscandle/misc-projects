package enemies;

import java.awt.Color;

public class Enemy {	
	private double x, y;
	private double offset = Math.random() * Math.PI * 2;
	private double radius = 0.5;
	public double angle = Math.random() * Math.PI * 2;
	
	public int levelSpawnedIn;
	
	private final static double ROTATION_SPEED = 0.015;
	
	private double speed;
	
	private double dilationX = 1;
	private double dilationY = 1;
	
	public int health;
		
    private static final Color[] colors = {
            //Color.decode("#ffffff"),
            Color.decode("#dae7f2"),
            Color.decode("#9de7f2"),
            Color.decode("#73c7e5"),
            Color.decode("#4192d9"),
            Color.decode("#3670b3"),
            Color.decode("#295ba6"),
            Color.decode("#23468c"),
            Color.decode("#1d2873"),
//            Color.decode("#2953a6"),
//            Color.decode("#3663b3"),
//            Color.decode("#417ed9"),
//            Color.decode("#73a8e5")
        };
	
	public Enemy(int health, double radius) {
		this.health = health;
		this.radius = radius;
	}
	
	protected double slope() {
		return Math.sin((x+offset)*dilationX)*dilationY;
	}
		
	//move based on arclength
	public void move() {
		double scaleFactor = Math.signum(speed) * Math.sqrt(Math.pow(speed, 2)/(1+Math.pow(slope(), 2)));
		
		x += scaleFactor;
		y += scaleFactor * slope();
		
		angle += ROTATION_SPEED;
	}
	
	//getters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getRadius() {
		return radius;
	}
	public Color getColor() {
		return colors[Math.max(Math.min(colors.length-1, health), 0)];
	}
	public double getSpeed() {
		return speed;
	}
	
	//setters
	public void setOrigin(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void randomizeDilation(double amount) {
		//equal chance of moving from the originial value
		dilationX *= Math.random() * amount - amount/2 + 1;
		dilationY *= Math.random() * amount * 10 - amount*5 + 1;
	}
	
	//toString
	public String toString() {
		return "x: " + x + ", y: " + y;
	}
	
}
