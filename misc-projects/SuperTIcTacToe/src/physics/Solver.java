package physics;

import java.util.ArrayList;
import java.util.List;

public class Solver {
	private List<Ball> balls = new ArrayList<>();

	private double stepInterval = 1.0 / 60;
	private double elapsedTime = 0;
	
	public void update(double timeSeconds) {
		this.elapsedTime += timeSeconds;
		
		while(this.elapsedTime > 0) {
			this.step();
			
			elapsedTime -= timeSeconds;
		}
	}

	private void step() {
		
	}
}
