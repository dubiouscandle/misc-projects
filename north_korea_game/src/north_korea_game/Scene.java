package north_korea_game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import scenes.Util;

public class Scene extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1202853094174486260L;
	// private static final double ASPECT_RATIO = 16.0 / 9;
	public static final int W = 800;
	public static final int H = 500;

	{
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	private GameFrame frame;

	private static final Cursor NUKE_CURSOR;

	static {
		Cursor nukeCursor = null;

		try {
			BufferedImage img = ImageIO.read(Util.getFile("cursor.png"));

			BufferedImage resized = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = resized.createGraphics();

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			g.drawImage(img, 0, 0, 50, 50, null);

			g.dispose();

			nukeCursor = Toolkit.getDefaultToolkit().createCustomCursor(resized, new Point(0, 0), "nuke");
		} catch (HeadlessException | IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
		}

		NUKE_CURSOR = nukeCursor;
	}

	{
		this.setCursor(NUKE_CURSOR);
	}

	public static Dimension getPreferredDimension() {
		return new Dimension(W, H);
	}

	public void close() {
	}

	public Scene() {
		this.setLayout(null);
		this.setPreferredSize(getPreferredDimension());
	}

	public GameFrame getFrame() {
		return frame;
	}

	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}

	public Graphics getTransformedGraphics(Graphics g) {
		g = g.create();

		g.drawRect(0, 0, this.getWidth(), this.getHeight());

		int width = this.getWidth();
		int height = this.getHeight();

		((Graphics2D) g).scale(1.0 / W * width, 1.0 / H * height);

		return g;
	}

}
