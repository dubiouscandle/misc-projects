package main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game {
	private static final int FRAME_RATE = 60;
	private static final double NS_PER_FRAME = 1000000000.0 / FRAME_RATE;

	private static final BufferedImage CURSOR_IMAGE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	private static final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(CURSOR_IMAGE,
			new Point(0, 0), null);

	private JFrame frame;
	private GamePanel panel;
	private GameState gameState;

	public Game() {
		frame = new JFrame("poly smash");
		gameState = new GameState();
		panel = new GamePanel(gameState);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setFocusable(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Add the mouse listener to the panel that updates the x and y coordinates of
		// the mouse
		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				gameState.handleKeyPressed(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
		panel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				gameState.handleMouseMovement(e);
			}

		});
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameState.handleMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		panel.setFocusable(true);
		panel.requestFocusInWindow();

		panel.setCursor(BLANK_CURSOR);
	}

	public void start() {
		// gamem loop
		long pastTime = System.nanoTime();
		double delta = 0;

		while (true) {
			// finds the difference between this iteration of the loop and the last and adds
			// it to delta in # of frames
			long currentTime = System.nanoTime();
			delta += (currentTime - pastTime) / NS_PER_FRAME;
			pastTime = currentTime;

			// if delta is greater than one frame, then update the game elements
			if (delta >= 1.0 / FRAME_RATE) {
				update();
				render();

				// subtract one frame-time from delta everytime the loop runs
				delta--;

				try {
					Thread.sleep((long) (0.9 * 1000.0 / FRAME_RATE));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void update() {
		// Update game logic
		gameState.update();

	}

	private void render() {
		// Render game graphics
		panel.repaint();
	}
}
