package ball_physics;

import java.awt.Color;
import java.util.ArrayList;

public class World {
	protected double elapsedTime = 0;

	protected ArrayList<Ball> balls = new ArrayList<>(500);

	protected int numSteps = 0;
	
	public static final double GRAVITY = -170;
	public static final double WORLD_WIDTH = Main.WORLD_SIZE;
	public static final double WORLD_HEIGHT = Main.WORLD_SIZE * 1.2;
	public static final double STEP_INTERVAL = Main.STEP_INTERVAL;
	public static final double CELL_SIZE = Ball.RADIUS * 2;
	public static final double INVERSE_CELL_SIZE = 1.0 / CELL_SIZE;

	public static final double LOWER_BOUND = Ball.RADIUS;
	public static final double UPPER_BOUND_X = WORLD_WIDTH - Ball.RADIUS;
	public static final double UPPER_BOUND_Y = WORLD_HEIGHT - Ball.RADIUS;

	public static final int NUM_CELLS_X = (int) Math.ceil(WORLD_WIDTH / CELL_SIZE);
	public static final int NUM_CELLS_Y = (int) Math.ceil(WORLD_HEIGHT / CELL_SIZE) + 20;

	public static final double LOWER_BOUND_X = LOWER_BOUND;

	@SuppressWarnings("unchecked")
	private final ArrayList<Ball>[][] partitioningGrid = new ArrayList[NUM_CELLS_X][NUM_CELLS_Y];

	public World() {
		for (int i = 0; i < NUM_CELLS_X; i++) {
			for (int j = 0; j < NUM_CELLS_Y; j++) {
				partitioningGrid[i][j] = new ArrayList<>();
			}
		}
	}

	public void step() {
		numSteps++;
		
		for (int i = 0; i < NUM_CELLS_X; i++) {
			for (int j = 0; j < NUM_CELLS_Y; j++) {
				partitioningGrid[i][j].clear();
			}

		}

		for (Ball ball : balls) {
			ball.vy += GRAVITY * STEP_INTERVAL;

			ball.x += ball.vx * STEP_INTERVAL;
			ball.y += ball.vy * STEP_INTERVAL;

			if (ball.x <= LOWER_BOUND) {
				ball.x = LOWER_BOUND;
				if (ball.vx < 0)
					ball.vx = -ball.vx * Ball.COEFFICIENT_OF_RESTITUTION;

			} else if (ball.x >= UPPER_BOUND_X) {
				ball.x = UPPER_BOUND_X;
				if (ball.vx > 0)
					ball.vx = -ball.vx * Ball.COEFFICIENT_OF_RESTITUTION;

			}

			if (ball.y <= LOWER_BOUND) {
				ball.y = LOWER_BOUND;
				if (ball.vy < 0)
					ball.vy = -ball.vy * Ball.COEFFICIENT_OF_RESTITUTION;

			} else if (ball.y >= UPPER_BOUND_Y) {
				ball.y = UPPER_BOUND_Y;
				if (ball.vy > 0)
					ball.vy = -ball.vy * Ball.COEFFICIENT_OF_RESTITUTION;

			}

			partitioningGrid[(int) (ball.x / CELL_SIZE)][(int) (ball.y / CELL_SIZE)].add(ball);

		}

		ArrayList<Ball> nearbyBalls = new ArrayList<>();

		for (int i = 1; i < NUM_CELLS_X - 1; i++) {
			for (int j = 1; j < NUM_CELLS_Y - 1; j++) {
				nearbyBalls.clear();

				nearbyBalls.addAll(partitioningGrid[i + 1][j - 1]);
				nearbyBalls.addAll(partitioningGrid[i + 1][j]);
				nearbyBalls.addAll(partitioningGrid[i + 1][j + 1]);

				nearbyBalls.addAll(partitioningGrid[i - 1][j - 1]);
				nearbyBalls.addAll(partitioningGrid[i - 1][j]);
				nearbyBalls.addAll(partitioningGrid[i - 1][j + 1]);

				nearbyBalls.addAll(partitioningGrid[i][j - 1]);
				nearbyBalls.addAll(partitioningGrid[i][j + 1]);

				for (int k = 0; k < nearbyBalls.size(); k++) {
					for (int l = k + 1; l < nearbyBalls.size(); l++) {
						Ball ball1 = nearbyBalls.get(k);
						Ball ball2 = nearbyBalls.get(l);

						ball1.handleCollision(ball2);
					}
				}

			}
		}

	}

	public boolean isOccupied(double x, double y) {
		int xInd = (int) (x / CELL_SIZE);
		int yInd = (int) (y / CELL_SIZE);

		ArrayList<Ball> nearbyBalls = new ArrayList<>();

		nearbyBalls.addAll(partitioningGrid[xInd + 1][yInd - 1]);
		nearbyBalls.addAll(partitioningGrid[xInd + 1][yInd]);
		nearbyBalls.addAll(partitioningGrid[xInd + 1][yInd + 1]);

		nearbyBalls.addAll(partitioningGrid[xInd - 1][yInd - 1]);
		nearbyBalls.addAll(partitioningGrid[xInd - 1][yInd]);
		nearbyBalls.addAll(partitioningGrid[xInd - 1][yInd + 1]);

		nearbyBalls.addAll(partitioningGrid[xInd][yInd - 1]);
		nearbyBalls.addAll(partitioningGrid[xInd][yInd]);
		nearbyBalls.addAll(partitioningGrid[xInd][yInd + 1]);

		for (int i = 0; i < nearbyBalls.size(); i++) {
			Ball ball = nearbyBalls.get(i);
			double dx = ball.x - x;
			double dy = ball.y - y;

			if (dx * dx + dy * dy <= Ball.DIAMETER_SQUARED)
				return true;
		}
		return false;
	}

	public void update(double timeSeconds) {
		elapsedTime += timeSeconds;

		while (elapsedTime >= STEP_INTERVAL) {
			this.step();

			elapsedTime -= STEP_INTERVAL;
		}

	}

	public boolean addBallIfUnobstructed(Color color, double x, double y, double vx, double vy) {
		if (!isOccupied(x, y)) {
			Ball ball = new Ball(x, y);

			ball.color = color;

			ball.vx = vx;
			ball.vy = vy;
			balls.add(ball);

			return true;
		}

		return false;

	}

	public void addBall(Ball ball) {
		this.balls.add(ball);

	}
}
