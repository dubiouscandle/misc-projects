package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class PoolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Font DEFAULT_DISPLAY_FONT = new Font("Courier new", 0, Game.SCREEN_HEIGHT / 20);

	private static final int POLE_THICKNESS = 13;
	private static final int POLE_LENGTH = 200;
	private static final double POLE_COLOR_RATIO = 0.3;
	private static final Color POLE_COLOR_1 = Color.decode("#D2B48C");
	private static final Color POLE_COLOR_2 = Color.decode("#964B00");

	private static final Color CAN_PLACE_CUE_BALL_COLOR = new Color(1f, 1f, 1f, 0.8f);
	private static final Color CAN_NOT_PLACE_CUE_BALL_COLOR = new Color(1f, 0f, 0f, 0.8f);

	private Game game;

	private int mouseX, mouseY;

	public PoolPanel() {
		InputManager inputManager = new InputManager();

		this.setBackground(Color.WHITE);
		this.addMouseListener(inputManager);
		this.addMouseMotionListener(inputManager);
		this.setPreferredSize(new Dimension(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g);

		Graphics2D g2 = (Graphics2D) g;

		if (game.getGameState() == GameState.PLAYER_1_TURN || game.getGameState() == GameState.PLAYER_2_TURN) {
			AffineTransform old = g2.getTransform();
			AffineTransform newTransform = new AffineTransform(old);

			newTransform.translate(Game.worldXToScreenX(game.getCueBall().getX()),
					Game.worldYToScreenY(game.getCueBall().getY()));

			double dx = game.getCueBall().getX() - Game.screenXToWorldX(mouseX);
			double dy = game.getCueBall().getY() - Game.screenYToWorldY(mouseY);

			double angle = Math.atan2(dx, dy);
			newTransform.rotate(angle);

			g2.setTransform(newTransform);

			int maxDrawDistance = (int) ((double) Game.STRENGTH_MAXIMUM / Game.STRENGTH_FACTOR
					* Game.SCALE_FACTOR_TO_SCREEN);
			int distance = (int) (Math.hypot(dx, dy) * Game.SCALE_FACTOR_TO_SCREEN);

			distance = Math.min(maxDrawDistance, distance);

			g.drawRect(-POLE_THICKNESS / 2, distance, POLE_THICKNESS, POLE_LENGTH);

			g.setColor(POLE_COLOR_1);
			g.fillRect(-POLE_THICKNESS / 2, distance, POLE_THICKNESS, (int) (POLE_LENGTH * POLE_COLOR_RATIO));
			g.setColor(POLE_COLOR_2);
			g.fillRect(-POLE_THICKNESS / 2, distance + (int) (POLE_LENGTH * POLE_COLOR_RATIO), POLE_THICKNESS,
					(int) (POLE_LENGTH * (1 - POLE_COLOR_RATIO)));

			g2.setTransform(old);

		} else if (game.getGameState() == GameState.PLAYER_1_BALL_IN_HAND
				|| game.getGameState() == GameState.PLAYER_2_BALL_IN_HAND) {
			int screenRadius = (int) (Ball.DEFAULT_BALL_RADIUS * Game.SCALE_FACTOR_TO_SCREEN);
			g.setColor(game.canPlaceCueBall(Game.screenXToWorldX(mouseX), Game.screenYToWorldY(mouseY))
					? CAN_PLACE_CUE_BALL_COLOR
					: CAN_NOT_PLACE_CUE_BALL_COLOR);
			g.fillOval(mouseX - screenRadius, mouseY - screenRadius, screenRadius * 2, screenRadius * 2);
			g.setColor(Color.BLACK);
			g.drawOval(mouseX - screenRadius, mouseY - screenRadius, screenRadius * 2, screenRadius * 2);
		}

		g.setColor(Color.BLACK);
		g.setFont(DEFAULT_DISPLAY_FONT);
		Game.drawStringCentered(g, "" + game.getGameState(), Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT / 10);
		Game.drawStringCentered(g, "player 1 texture: " + game.getPlayer1Texture(), Game.SCREEN_WIDTH / 2,
				Game.SCREEN_HEIGHT * 19 / 20);
		Game.drawStringCentered(g, "player 2 texture: " + game.getPlayer2Texture(), Game.SCREEN_WIDTH / 2,
				Game.SCREEN_HEIGHT * 18 / 20);

	}

	private class InputManager implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			this.mouseMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (game.getGameState() == GameState.PLAYER_1_TURN || game.getGameState() == GameState.PLAYER_2_TURN) {
				game.shootCueBall(Game.screenXToWorldX(e.getX()), Game.screenYToWorldY(e.getY()));
			} else if (game.getGameState() == GameState.PLAYER_1_BALL_IN_HAND
					|| game.getGameState() == GameState.PLAYER_2_BALL_IN_HAND) {
				game.placeCueBall(Game.screenXToWorldX(e.getX()), Game.screenYToWorldY(e.getY()));
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
}
