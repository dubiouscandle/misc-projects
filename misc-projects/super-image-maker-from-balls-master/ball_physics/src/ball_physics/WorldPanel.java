package ball_physics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class WorldPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected Image img;

	private static final int PANEL_SIZE = 800;
	private static final double SCALE_FACTOR_TO_SCREEN = (double) PANEL_SIZE / World.WORLD_WIDTH;

	World world;

	public WorldPanel(World world) {
		this.world = world;

		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
	}

	public WorldPanel(World world, BufferedImage img) {
		this(world);
		this.img = img;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (img != null)
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);

		@SuppressWarnings("unchecked")
		List<Ball> balls = (List<Ball>) world.balls.clone();

		for (Ball ball : balls) {
			int screenX = (int) (ball.x * SCALE_FACTOR_TO_SCREEN);
			int screenY = PANEL_SIZE - (int) (ball.y * SCALE_FACTOR_TO_SCREEN);
			int screenRadius = (int) (Ball.RADIUS * SCALE_FACTOR_TO_SCREEN);

			g.setColor(ball.color);
			g.fillOval(screenX - screenRadius, screenY - screenRadius, screenRadius * 2, screenRadius * 2);
		}
//		for (Ball ball : world.balls) {
//			int screenX = (int) (ball.px * SCALE_FACTOR_TO_SCREEN);
//			int screenY = PANEL_SIZE - (int) (ball.py * SCALE_FACTOR_TO_SCREEN);
//			int screenRadius = (int) (Ball.RADIUS * SCALE_FACTOR_TO_SCREEN);
//
//			g.setColor(Color.PINK);
//			g.fillOval(screenX - screenRadius, screenY - screenRadius, screenRadius * 2, screenRadius * 2);
//		}
	}

}
