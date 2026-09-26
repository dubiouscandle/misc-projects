package trig_functions_visually;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	public static void main(String[] args) {
		Renderer renderer = new Renderer();

		JFrame frame = new JFrame();
		frame.add(renderer);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		double v = 0.001;

		while (true) {
			renderer.theta += v;
			v += 0.001;
			renderer.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Renderer extends JPanel {
		private static final long serialVersionUID = 1L;

		private double theta = 0;

		private static final int SCREEN_RADIUS = 300;

		private static final int SCREEN_HEIGHT = 800;
		private static final int SCREEN_WIDTH = 800;
		private static final int HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2;
		private static final int HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;

		public Renderer() {
			super();
			this.setBackground(Color.BLACK);
			this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.scale(1, -1);
			g2.translate(0, -SCREEN_HEIGHT);

			g2.setStroke(new BasicStroke(2));
			g.setColor(Color.WHITE);

			g2.drawOval(HALF_SCREEN_WIDTH - SCREEN_RADIUS, HALF_SCREEN_HEIGHT - SCREEN_RADIUS, SCREEN_RADIUS * 2,
					SCREEN_RADIUS * 2);

			double sin = Math.sin(theta);
			double cos = Math.cos(theta);

			double sec = 1.0 / cos;
			double csc = 1.0 / sin;

			int screenCsc = (int) (csc * SCREEN_RADIUS);
			int screenSec = (int) (sec * SCREEN_RADIUS);
			int screenSin = (int) (sin * SCREEN_RADIUS);
			int screenCos = (int) (cos * SCREEN_RADIUS);

			// unit line
			g2.drawLine(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT, HALF_SCREEN_WIDTH + (int) (cos * SCREEN_RADIUS),
					HALF_SCREEN_HEIGHT + (int) (sin * SCREEN_RADIUS));

			// sec
			g.setColor(Color.BLUE);
			g2.drawLine(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT, HALF_SCREEN_WIDTH + screenSec, HALF_SCREEN_HEIGHT);

			// tan
			g.setColor(Color.ORANGE);
			g2.drawLine(HALF_SCREEN_WIDTH + screenSec, HALF_SCREEN_HEIGHT,
					HALF_SCREEN_WIDTH + (int) (cos * SCREEN_RADIUS), HALF_SCREEN_HEIGHT + (int) (sin * SCREEN_RADIUS));

			// csc
			g2.drawLine(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT, HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT + screenCsc);

			// cot
			g.setColor(Color.YELLOW);
			g2.drawLine(HALF_SCREEN_WIDTH + (int) (cos * SCREEN_RADIUS),
					HALF_SCREEN_HEIGHT + (int) (sin * SCREEN_RADIUS), HALF_SCREEN_WIDTH,
					HALF_SCREEN_HEIGHT + screenCsc);

			// sin and cos
			g.setColor(Color.GREEN);
			g2.drawLine(HALF_SCREEN_WIDTH, HALF_SCREEN_HEIGHT, HALF_SCREEN_WIDTH + screenCos, HALF_SCREEN_HEIGHT);
			g.setColor(Color.RED);
			g2.drawLine(HALF_SCREEN_WIDTH + screenCos, HALF_SCREEN_HEIGHT, HALF_SCREEN_WIDTH + screenCos,
					HALF_SCREEN_HEIGHT + screenSin);
		}

	}
}
