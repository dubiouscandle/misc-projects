package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		PoolPanel poolPanel = new PoolPanel();

		Game game = new Game();

		poolPanel.setGame(game);

		ResultHandler r = new ResultHandler() {
			@Override
			public void handleGameResult(int winner) {
				Game g1 = new Game();
				g1.setResultHandler(this);

				poolPanel.setGame(g1);

			}
		};

		game.setResultHandler(r);

		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.add(poolPanel, BorderLayout.CENTER);

		frame.getContentPane().setCursor(getDotCursor());
		frame.requestFocus();

		frame.setResizable(false);
		frame.setBackground(Color.PINK);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int frameRate = 240;
		double frameIntervalSeconds = 1.0 / frameRate;
		long frameIntervalNS = 1_000_000_000 / frameRate;

		long delta = 0;
		long pTime = System.nanoTime();
		long currentTime = System.nanoTime();

		while (true) {
			currentTime = System.nanoTime();

			delta += currentTime - pTime;

			if (delta > frameIntervalNS) {
				if (game.getGameState() == GameState.PLAYER_1_PHYSICS
						|| game.getGameState() == GameState.PLAYER_2_PHYSICS) {
					
					poolPanel.repaint();
					poolPanel.getGame().update(frameIntervalSeconds);
				}
				delta -= frameIntervalNS;
			}

			pTime = currentTime;
						
			try {
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static Cursor getDotCursor() {
		int cursorSize = 13; // Size of the cursor image
		BufferedImage cursorImage = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = cursorImage.createGraphics();

		// Draw a dot in the center of the image
		g2d.setColor(new Color(0, 0, 0, 0.6f)); // You can change the color of the dot
		int dotSize = cursorSize; // Size of the dot
		g2d.fillOval(cursorSize / 2 - dotSize / 2, cursorSize / 2 - dotSize / 2, dotSize, dotSize); // Draw a dot in the
																									// center
		g2d.dispose();

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Set the hot spot to the center of the cursor image
		Cursor dotCursor = toolkit.createCustomCursor(cursorImage, new Point(cursorSize / 2, cursorSize / 2), "dot");

		return dotCursor;
	}
}
