package turtle_drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawer {
	private BufferedImage img;
	private boolean penDown = false;
	private Graphics2D g2;
	private float x, y;
	private int r = 0, g = 0, b = 0, a = 255;

	public Drawer(int virtualWidth, int virtualHeight) {
		img = new BufferedImage(virtualWidth, virtualHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) img.createGraphics();

		Graphics g = img.createGraphics();
		g.drawRect(0, 0, virtualWidth, virtualHeight);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setPen(boolean penDown) {
		this.penDown = penDown;
	}

	public void setColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void moveTo(float x, float y) {
		if (penDown) {
			drawLine(this.x, this.y, x, y);
		}
		this.x = x;
		this.y = y;
	}

	private void drawLine(float x1, float y1, float x2, float y2) {
		g2.setColor(new Color(r, g, b, a));
		g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public void move(float dx, float dy) {
		if (penDown) {
			drawLine(this.x, this.y, this.x + dx, this.y + dy);
		}

		x += dx;
		y += dy;
	}
}
