package com.dubiouscandle.dragoncurvegenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DragonCurveRenderer {
	public static final int MAX_DEPTH = 28;
	public static final Color CURVE_COLOR = new Color(104, 136, 190);
	private static int n;
	private static int r;

	public static void main(String[] args) {
		n = 1;
		r = 0;

		SwingUtilities.invokeLater(() -> {
			DragonPanel panel = new DragonPanel(new DragonCurve(n, r));
			panel.setBackground(Color.BLACK);
			JFrame frame = new JFrame();

			frame.setPreferredSize(new Dimension(800, 800));
			frame.setFocusable(true);
			frame.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						n++;
						if (n > MAX_DEPTH) {
							n = MAX_DEPTH;
						}
						panel.dragonCurve = new DragonCurve(n, r);
						panel.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						n--;
						if (n < 1) {
							n = 1;
						}
						panel.dragonCurve = new DragonCurve(n, r);
						panel.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						panel.strokeWeight++;
						panel.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						panel.strokeWeight--;
						if (panel.strokeWeight < 1) {
							panel.strokeWeight = 1;
						}
						panel.repaint();
					} else if (e.getKeyCode() == KeyEvent.VK_R) {
						r++;
						panel.dragonCurve = new DragonCurve(n, r);
						panel.repaint();
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}
			});
			frame.add(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

	public static class DragonPanel extends JPanel {
		private static final long serialVersionUID = 5142150909279659879L;

		private int strokeWeight = 1;
		private DragonCurve dragonCurve;

		public DragonPanel(DragonCurve dc) {
			dragonCurve = dc;
		}

		@Override
		public void paintComponent(Graphics g) {
			System.out.println("DRAWING " + n);

			super.paintComponent(g);

			dragonCurve.reset();

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(strokeWeight));
			g2.setColor(CURVE_COLOR);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			int padding = 20;
			int availableWidth = getWidth() - 2 * padding;
			int availableHeight = getHeight() - 2 * padding;

			int intLineLength;

			if (dragonCurve.width == 0) {
				intLineLength = availableHeight / dragonCurve.height;
			} else if (dragonCurve.height == 0) {
				intLineLength = availableHeight / dragonCurve.width;
			} else {
				intLineLength = Math.min(availableWidth / dragonCurve.width, availableHeight / dragonCurve.height);
			}
			if (intLineLength >= 1) {
				int tx = (getWidth() - dragonCurve.width * intLineLength) / 2;
				int ty = (getHeight() - dragonCurve.height * intLineLength) / 2;

				int px = tx + dragonCurve.x * intLineLength;
				int py = ty + dragonCurve.y * intLineLength;

				while (dragonCurve.hasNext()) {
					dragonCurve.next();
					int x = tx + dragonCurve.x * intLineLength;
					int y = ty + dragonCurve.y * intLineLength;

					g2.drawLine(px, py, x, y);
					px = x;
					py = y;
				}
			} else {
				double scaleX = (double) availableWidth / dragonCurve.width;
				double scaleY = (double) availableHeight / dragonCurve.height;
				double scale = Math.min(scaleX, scaleY);

				double tx = (getWidth() - dragonCurve.width * scale) / 2.0;
				double ty = (getHeight() - dragonCurve.height * scale) / 2.0;

				while (dragonCurve.hasNext()) {
					dragonCurve.next();
					int x = (int) (tx + dragonCurve.x * scale);
					int y = (int) (ty + dragonCurve.y * scale);
					g2.drawLine(x, y, x, y);
				}
			}
		}
	}

	public static class DragonCurve {
		protected final int len;
		protected int i;
		protected int dir;
		protected int x, y;
		protected final int width, height;
		private final int xMin, yMin;
		private final int r;

		public boolean hasNext() {
			return i <= len;
		}

		public void next() {
			if (dir == 0) {
				x++;
			} else if (dir == 1) {
				y++;
			} else if (dir == 2) {
				x--;
			} else {
				y--;
			}
			dir = (dir + (dragonTurn(i) == 1 ? 1 : -1)) & 3;

			i++;
		}

		public void reset() {
			dir = r;
			x = -xMin;
			y = -yMin;
			i = 1;
		}

		public DragonCurve(int n, int r) {
			this.r = r;
			len = 1 << n;
			dir = r;
			x = 0;
			y = 0;
			i = 1;

			int xMin = 0;
			int xMax = 0;
			int yMin = 0;
			int yMax = 0;
			for (int i = 1; i <= len; i++) {
				if (dir == 0) {
					x++;
					if (x > xMax) {
						xMax = x;
					}
				} else if (dir == 1) {
					y++;
					if (y > yMax) {
						yMax = y;
					}
				} else if (dir == 2) {
					x--;
					if (x < xMin) {
						xMin = x;
					}
				} else {
					y--;
					if (y < yMin) {
						yMin = y;
					}
				}
				dir = (dir + (dragonTurn(i) == 1 ? 1 : -1)) & 3;
			}

			width = xMax - xMin;
			height = yMax - yMin;

			dir = 0;
			x = -xMin;
			y = -yMin;

			this.xMin = xMin;
			this.yMin = yMin;

			reset();
		}
	}

	public static int dragonTurn(int n) {
		while ((n & 1) == 0) {
			n >>= 1;
		}

		return (n >> 1) & 1;
	}
}
