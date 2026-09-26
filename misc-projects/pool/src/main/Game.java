package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
	// visual
	private static final Color POOL_FELT_COLOR = Color.decode("#254636");
	private static final Color POOL_WALL_COLOR = Color.decode("#F7E7CE");
	private static final double STANDARD_POOL_MARGIN = 30;
	public static final double SCALE_FACTOR_TO_SCREEN = 5.2;

	public static final double STRENGTH_FACTOR = 30;
	public static final double STRENGTH_MAXIMUM = 700;

	private static final double WALL_RESTITUTION = 0.87;
	private static final double POOL_FRICTION_COEFFICIENT = 0.65;
	private static final double MINIMUM_ENERGY = 0.55;

	private static final int NUM_BALLS_PER_PLAYER = 7;
	private static final double INITIAL_BALL_SPREAD = 0;

	private static final double STANDARD_CORNER_HOLE_RADIUS = 12.7 * 1.5 / 2;
	private static final double STANDARD_EDGE_HOLE_RADIUS = 12.7 * 1.5 / 2.7;

	private static final double STANDARD_POOL_PLAYING_AREA_WIDTH = 198;
	private static final double STANDARD_POOL_PLAYING_AREA_HEIGHT = 99;

	// dependent constants
	private static final int NUM_POINTS_TO_WIN = NUM_BALLS_PER_PLAYER + 1;
	private static final double STANDARD_POOL_WIDTH_CM = STANDARD_POOL_PLAYING_AREA_WIDTH + STANDARD_POOL_MARGIN * 2;
	private static final double STANDARD_POOL_HEIGHT_CM = STANDARD_POOL_PLAYING_AREA_HEIGHT + STANDARD_POOL_MARGIN * 2;

	public static final int SCREEN_WIDTH = (int) (SCALE_FACTOR_TO_SCREEN * STANDARD_POOL_WIDTH_CM);
	public static final int SCREEN_HEIGHT = (int) (SCALE_FACTOR_TO_SCREEN * STANDARD_POOL_HEIGHT_CM);

	private static final Font DEFAULT_BALL_FONT = new Font("Courier new", 0,
			(int) (Ball.DEFAULT_BALL_RADIUS * SCALE_FACTOR_TO_SCREEN));

	private static final double RIGHT_BOUND = STANDARD_POOL_WIDTH_CM / 2 - STANDARD_POOL_MARGIN;
	private static final double LEFT_BOUND = -STANDARD_POOL_WIDTH_CM / 2 + STANDARD_POOL_MARGIN;
	private static final double TOP_BOUND = STANDARD_POOL_HEIGHT_CM / 2 - STANDARD_POOL_MARGIN;
	private static final double BOTTOM_BOUND = -STANDARD_POOL_HEIGHT_CM / 2 + STANDARD_POOL_MARGIN;

	private static final int PLAYER_1 = 1;
	private static final int PLAYER_2 = 2;

	private ResultHandler resultHandler;

	private int player1Points = 0;
	private int player2Points = 0;
	private Texture player1Texture = null;
	private Texture player2Texture = null;

	private boolean cueBallIsInHole = false;

	private boolean isBallScoredYet = false;

	private GameState gameState = GameState.PLAYER_1_TURN;
	private boolean playerScoredSelfBall = false, playerScoredOtherBall = false;

	private List<Ball> balls = new ArrayList<>();
	private Hole[] holes = new Hole[6];
	private Ball cueBall;

	public Game() {
		this.resetCueBall();

		this.holes[0] = new Hole(LEFT_BOUND, TOP_BOUND, STANDARD_CORNER_HOLE_RADIUS);
		this.holes[1] = new Hole(RIGHT_BOUND, TOP_BOUND, STANDARD_CORNER_HOLE_RADIUS);
		this.holes[2] = new Hole(LEFT_BOUND, BOTTOM_BOUND, STANDARD_CORNER_HOLE_RADIUS);
		this.holes[3] = new Hole(RIGHT_BOUND, BOTTOM_BOUND, STANDARD_CORNER_HOLE_RADIUS);

		this.holes[4] = new Hole((LEFT_BOUND + RIGHT_BOUND) / 2, TOP_BOUND, STANDARD_EDGE_HOLE_RADIUS);
		this.holes[5] = new Hole((LEFT_BOUND + RIGHT_BOUND) / 2, BOTTOM_BOUND, STANDARD_EDGE_HOLE_RADIUS);

		setupTriangle();
	}

	private void resetCueBall() {
		this.cueBall = newCueBall();

		balls.add(cueBall);

		cueBall.setVelocity(0, 0);
		cueBall.setX(-STANDARD_POOL_PLAYING_AREA_WIDTH / 4.0);
		cueBall.setY(0);
	}

	public void setupTriangle() {
		balls.clear();

		resetCueBall();

		Ball b;

		for (int i = 1; i <= NUM_BALLS_PER_PLAYER; i++) {
			b = new Ball(i, Texture.SOLID);
			balls.add(b);
			b.setLocation(
					Math.random() * INITIAL_BALL_SPREAD - INITIAL_BALL_SPREAD / 2
							+ STANDARD_POOL_PLAYING_AREA_WIDTH / 4.0,
					Math.random() * INITIAL_BALL_SPREAD - INITIAL_BALL_SPREAD / 2);

			b = new Ball(i + NUM_BALLS_PER_PLAYER + 1, Texture.STRIPED);
			balls.add(b);
			b.setLocation(
					Math.random() * INITIAL_BALL_SPREAD - INITIAL_BALL_SPREAD / 2
							+ STANDARD_POOL_PLAYING_AREA_WIDTH / 4.0,
					Math.random() * INITIAL_BALL_SPREAD - INITIAL_BALL_SPREAD / 2);

			this.update(0);

			System.out.println(i);

		}

		b = new Ball(NUM_BALLS_PER_PLAYER + 1, Texture.EIGHT_BALL);
		balls.add(b);
		b.setLocation(STANDARD_POOL_PLAYING_AREA_WIDTH / 4.0, 0);
		this.update(0);

		System.out.println("initialization finished");

	}

	public void update(double timeSeconds) {
		Iterator<Ball> iterator = balls.iterator();

		while (iterator.hasNext()) {
			Ball ball = iterator.next();

			// handle ball on ball collision
			for (Ball ball2 : balls) {
				if (ball == ball2)
					continue;

				if (ball.isCollidingWith(ball2)) {
					ball.handleCollision(ball2);
				}
			}

			// handle ball on wall collision
			if (ball.getX() + ball.getRadius() >= RIGHT_BOUND && ball.getxVelocity() > 0) {
				ball.setxVelocity(-ball.getxVelocity() * ball.getRestution() * WALL_RESTITUTION);
				ball.setX(RIGHT_BOUND - ball.getRadius());
			}
			if (ball.getX() - ball.getRadius() <= LEFT_BOUND && ball.getxVelocity() < 0) {
				ball.setxVelocity(-ball.getxVelocity() * ball.getRestution() * WALL_RESTITUTION);
				ball.setX(LEFT_BOUND + ball.getRadius());
			}
			if (ball.getY() + ball.getRadius() >= TOP_BOUND && ball.getyVelocity() > 0) {
				ball.setyVelocity(-ball.getyVelocity() * ball.getRestution() * WALL_RESTITUTION);
				ball.setY(TOP_BOUND - ball.getRadius());
			}
			if (ball.getY() - ball.getRadius() <= BOTTOM_BOUND && ball.getyVelocity() < 0) {
				ball.setyVelocity(-ball.getyVelocity() * ball.getRestution() * WALL_RESTITUTION);
				ball.setY(BOTTOM_BOUND + ball.getRadius());
			}

			double xForce = -ball.getxVelocity() * POOL_FRICTION_COEFFICIENT;
			double yForce = -ball.getyVelocity() * POOL_FRICTION_COEFFICIENT;

			ball.setxVelocity(ball.getxVelocity() + xForce * timeSeconds);
			ball.setyVelocity(ball.getyVelocity() + yForce * timeSeconds);

			// update the ball using its new velocity
			ball.update(timeSeconds);

			// check if ball is in the hole
			for (Hole hole : holes) {
				if (!hole.ballCenterOverlaps(ball))
					continue;

				if (ball.getTexture() == Texture.CUE_BALL) {
					cueBallIsInHole = true;
					iterator.remove();
					continue;

				}

				if (ball.getTexture() == Texture.EIGHT_BALL) {
					if (this.gameState == GameState.PLAYER_1_PHYSICS) {
						if (player1Points == NUM_POINTS_TO_WIN - 1)
							resultHandler.handleGameResult(PLAYER_1);
						else
							resultHandler.handleGameResult(PLAYER_2);
					} else if (this.gameState == GameState.PLAYER_2_PHYSICS) {
						if (player2Points == NUM_POINTS_TO_WIN - 1)
							resultHandler.handleGameResult(PLAYER_2);
						else
							resultHandler.handleGameResult(PLAYER_1);
					}
				}

				// only gets called once
				if (!isBallScoredYet) {
					if (this.gameState == GameState.PLAYER_1_PHYSICS) {
						player1Texture = ball.getTexture();
						player2Texture = ball.getTexture().getOpposite();
					} else if (this.gameState == GameState.PLAYER_2_PHYSICS) {
						player2Texture = ball.getTexture();
						player1Texture = ball.getTexture().getOpposite();
					}

					isBallScoredYet = true;
				}

				if (ball.getTexture() == player1Texture) {
					if (gameState == GameState.PLAYER_1_PHYSICS)
						playerScoredSelfBall = true;
					else
						playerScoredOtherBall = true;

					player1Points++;
				} else if (ball.getTexture() == player2Texture) {
					if (gameState == GameState.PLAYER_2_PHYSICS)
						playerScoredSelfBall = true;
					else
						playerScoredOtherBall = true;

					player2Points++;
				}

				iterator.remove();

			}
		}

		if (this.getTotalEnergy() <= MINIMUM_ENERGY) {
			if (gameState == GameState.PLAYER_1_PHYSICS) {
				if (cueBallIsInHole) {
					gameState = GameState.PLAYER_2_BALL_IN_HAND;
				} else if (playerScoredSelfBall && !playerScoredOtherBall) {
					gameState = GameState.PLAYER_1_TURN;
				} else
					gameState = GameState.PLAYER_2_TURN;

			} else if (gameState == GameState.PLAYER_2_PHYSICS) {
				if (cueBallIsInHole) {
					gameState = GameState.PLAYER_1_BALL_IN_HAND;
				} else if (playerScoredSelfBall && !playerScoredOtherBall) {
					gameState = GameState.PLAYER_2_TURN;
				} else
					gameState = GameState.PLAYER_1_TURN;
			}

			for (Ball ball : balls) {
				ball.setVelocity(0, 0);
			}

			cueBallIsInHole = false;
			playerScoredSelfBall = false;
			playerScoredOtherBall = false;

		}

	}

	public void draw(Graphics g) {
		g.setColor(POOL_WALL_COLOR);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		g.setColor(POOL_FELT_COLOR);
		g.fillRect(worldXToScreenX(LEFT_BOUND), worldYToScreenY(TOP_BOUND),
				(int) ((RIGHT_BOUND - LEFT_BOUND) * SCALE_FACTOR_TO_SCREEN),
				(int) ((TOP_BOUND - BOTTOM_BOUND) * SCALE_FACTOR_TO_SCREEN));
		g.setColor(Color.BLACK);
		g.drawRect(worldXToScreenX(LEFT_BOUND), worldYToScreenY(TOP_BOUND),
				(int) ((RIGHT_BOUND - LEFT_BOUND) * SCALE_FACTOR_TO_SCREEN),
				(int) ((TOP_BOUND - BOTTOM_BOUND) * SCALE_FACTOR_TO_SCREEN));

		for (Hole hole : holes) {
			int screenRadius = (int) (hole.getRadius() * SCALE_FACTOR_TO_SCREEN);
			int x = worldXToScreenX(hole.getX());
			int y = worldYToScreenY(hole.getY());
			g.setColor(Color.BLACK);
			g.fillOval(x - screenRadius, y - screenRadius, screenRadius * 2, screenRadius * 2);
		}

		// ball drawing logic
		Iterator<Ball> iterator = balls.iterator();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform old = g2.getTransform();

		while (iterator.hasNext()) {
			Ball ball = iterator.next();

			int screenRadius = (int) (ball.getRadius() * SCALE_FACTOR_TO_SCREEN);

			int x = worldXToScreenX(ball.getX());
			int y = worldYToScreenY(ball.getY());

			AffineTransform newTransform = new AffineTransform(old);

			newTransform.translate(x, y);

			g2.setTransform(newTransform);

			if (ball.getTexture() == Texture.SOLID || ball.getTexture() == Texture.CUE_BALL
					|| ball.getTexture() == Texture.EIGHT_BALL) {
				g.setColor(ball.getColor());
				g.fillOval(-screenRadius, -screenRadius, screenRadius * 2, screenRadius * 2);

			} else if (ball.getTexture() == Texture.STRIPED) {
				drawStripedBall(g, ball, 0, 0, screenRadius);
			}

			g.setFont(DEFAULT_BALL_FONT);

			g.setColor(Color.WHITE);
			g.fillOval(-screenRadius / 2, -screenRadius / 2, screenRadius, screenRadius);
			g.setColor(Color.BLACK);

			if (ball.getNumber() != 0)
				drawStringCentered(g, "" + ball.getNumber(), 0, 0);
			g.drawOval(-screenRadius, -screenRadius, screenRadius * 2, screenRadius * 2);

			g2.setTransform(old);

		}
	}

	public static int worldXToScreenX(double worldX) {
		return (int) (worldX * SCALE_FACTOR_TO_SCREEN + SCREEN_WIDTH / 2);
	}

	public static int worldYToScreenY(double worldY) {
		return (int) (-worldY * SCALE_FACTOR_TO_SCREEN + SCREEN_HEIGHT / 2);
	}

	public static double screenXToWorldX(int screenX) {
		return (screenX - SCREEN_WIDTH / 2) / SCALE_FACTOR_TO_SCREEN;
	}

	public static double screenYToWorldY(int screenY) {
		return (screenY - SCREEN_HEIGHT / 2) / -SCALE_FACTOR_TO_SCREEN;
	}

	public GameState getGameState() {
		return gameState;
	}

	private static void drawStripedBall(Graphics g, Ball ball, int x, int y, int screenRadius) {
		g.setColor(ball.getColor());

		int numStripes = 10; // Number of stripes (adjust as needed)
		for (int i = 0; i < numStripes; i++) {
			int startAngle = i * (360 / numStripes);
			int endAngle = (i + 1) * (360 / numStripes);

			g.setColor(i % 2 == 0 ? Color.WHITE : ball.getColor()); // Alternate stripe colors
			g.fillArc(x - screenRadius, y - screenRadius, screenRadius * 2, screenRadius * 2, startAngle,
					endAngle - startAngle);
		}
	}

	public double getTotalEnergy() {
		double sum = 0;

		for (Ball ball : balls) {
			double speed = ball.getSpeed();
			sum += ball.getMass() * speed * speed;
		}

		return sum * 0.5;
	}

	public void shootCueBall(double x, double y) {
		if (this.gameState == GameState.PLAYER_1_TURN)
			this.gameState = GameState.PLAYER_1_PHYSICS;
		else if (this.gameState == GameState.PLAYER_2_TURN)
			this.gameState = GameState.PLAYER_2_PHYSICS;

		double dx = cueBall.getX() - x;
		double dy = cueBall.getY() - y;

		double angle = Math.atan2(dy, dx);

		double magnitude = Math.sqrt(dx * dx + dy * dy) * STRENGTH_FACTOR;

		if (magnitude > STRENGTH_MAXIMUM)
			magnitude = STRENGTH_MAXIMUM;

		cueBall.setVelocity(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);

	}

	public void placeCueBall(double x, double y) {
		if (!canPlaceCueBall(x, y))
			return;

		this.cueBall = newCueBall();

		balls.add(cueBall);

		cueBall.setLocation(x, y);

		if (this.gameState == GameState.PLAYER_1_BALL_IN_HAND)
			this.gameState = GameState.PLAYER_1_TURN;
		else if (this.gameState == GameState.PLAYER_2_BALL_IN_HAND)
			this.gameState = GameState.PLAYER_2_TURN;

	}

	private static Ball newCueBall() {
		Ball b = new Ball(0, Texture.CUE_BALL);
		return b;
	};

	public static void drawStringCentered(Graphics g, String s, int x, int y) {
		FontMetrics metrics = g.getFontMetrics();

		g.drawString(s, x - metrics.stringWidth(s) / 2, y + metrics.getAscent() / 2 - 1);
	}

	public Ball getCueBall() {
		return cueBall;
	}

	public void setResultHandler(ResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	}

	public boolean canPlaceCueBall(double x, double y) {
		for (Ball ball : balls) {
			double dx = ball.getX() - x;
			double dy = ball.getY() - y;
			double sumRadius = ball.getRadius() * 2;
			if (dx * dx + dy * dy <= sumRadius * sumRadius)
				return false;
		}

		for (Hole hole : holes)
			if (hole.pointOverlaps(x, y))
				return false;

		if (x - cueBall.getRadius() >= LEFT_BOUND && x + cueBall.getRadius() <= RIGHT_BOUND
				&& y - cueBall.getRadius() >= BOTTOM_BOUND && y + cueBall.getRadius() <= TOP_BOUND)
			return true;

		return false;

	}

	public Texture getPlayer1Texture() {
		return this.player1Texture;
	}

	public Texture getPlayer2Texture() {
		return this.player2Texture;
	}

}
