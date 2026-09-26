package main;

public class Hole {
	private double x, y, radius;
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Hole(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		
		this.setRadius(radius);
	};
	
	public boolean ballCenterOverlaps(Ball ball) {
		double dx = ball.getX() - x;
		double dy = ball.getY() - y;
		
		return dx * dx + dy * dy <= this.radius * this.radius;
	}
	public boolean pointOverlaps(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		
		return dx * dx + dy * dy <= this.radius * this.radius;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
