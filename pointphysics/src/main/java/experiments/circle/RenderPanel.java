package experiments.circle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.dubiouscandle.pointphysics.Body;
import com.dubiouscandle.pointphysics.BodyList;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = -5769428947675129568L;
	private BodyList bodyList;
	BufferedImage img;
	{
		try {
			img = ImageIO.read(new File("src/main/java/experiments/circle/smily.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private HashMap<Float, Color> colors = new HashMap<>();

	private Color getColor(float radius) {
		if (colors.containsKey(radius)) {
			return colors.get(radius);
		} else {
			Random random = new Random(Float.hashCode(radius) + 4321983);
			Color color = new Color(Color.HSBtoRGB(random.nextFloat(), 1, 1));
			colors.put(radius, color);
			return color;
		}

	}

	private float cameraX = 0, cameraY = 0, worldWidth = 600, worldHeight = 600;

	public RenderPanel(BodyList bodyList) {
		this.bodyList = bodyList;

		setPreferredSize(new Dimension(800, 800));
		setBackground(Color.black);

		MouseListener mouseListener = new MouseListener();
		this.addMouseMotionListener(mouseListener);
		this.addMouseWheelListener(mouseListener);
	}

	private class MouseListener implements MouseWheelListener, MouseMotionListener {
		private float px, py;

		@Override
		public void mouseDragged(MouseEvent e) {
			float size = Math.min(getWidth(), getHeight());
			float scaleX = size / worldWidth;
			float scaleY = size / worldHeight;
			float x = e.getX() / scaleX;
			float y = e.getY() / scaleY;

			cameraX += (px - x);
			cameraY -= (py - y);

			px = x;
			py = y;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			float size = Math.min(getWidth(), getHeight());
			float scaleX = size / worldWidth;
			float scaleY = size / worldHeight;
			px = e.getX() / scaleX;
			py = e.getY() / scaleY;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			final float sens = 0.1f;
			worldWidth *= 1 + sens * (float) e.getPreciseWheelRotation();
			worldHeight *= 1 + sens * (float) e.getPreciseWheelRotation();

			float size = Math.min(getWidth(), getHeight());
			float scaleX = size / worldWidth;
			float scaleY = size / worldHeight;
			px = e.getX() / scaleX;
			py = e.getY() / scaleY;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		float size = Math.min(getWidth(), getHeight());
		float scaleX = size / worldWidth;
		float scaleY = size / worldHeight;

//	    // draw your static big image
//	    {
//	        float x = 0, y = 0, r = 300;
//	        int screenX   = (int) ((x - cameraX) * scaleX + getWidth() / 2);
//	        int screenY   = (int) (getHeight() / 2 - (y - cameraY) * scaleY);
//	        int diameterX = Math.max((int) (r * 2 * scaleX), 3);
//	        int diameterY = Math.max((int) (r * 2 * scaleY), 3);
//	        g2.drawImage(img,
//	                     screenX - diameterX/2,
//	                     screenY - diameterY/2,
//	                     diameterX, diameterY,
//	                     null);
//	    }

		// ── REPLACED LOOP START ──
		PrintWriter out = null;
		StringBuilder sb =new StringBuilder();
		try {
			out = new PrintWriter("src/main/java/experiments/circle/cols.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Body body : bodyList) {
			float x = body.x;
			float y = body.y;
			float r = body.radius;

			// 1) compute on-screen circle
			int screenX = (int) ((x - cameraX) * scaleX + getWidth() / 2);
			int screenY = (int) (getHeight() / 2 - (y - cameraY) * scaleY);
			int diameterX = Math.max((int) (r * 2 * scaleX), 3);
			int diameterY = Math.max((int) (r * 2 * scaleY), 3);

			// 2) map world‐coords → normalized [0,1] across your panel
			float normX = (x - cameraX) / worldWidth + 0.5f;
			float normY = (cameraY - y) / worldHeight + 0.5f;
			// ↑ note the Y flip so +Y is “up” on screen

			// 3) sample img at that normalized spot
			int imgX = (int) (normX * img.getWidth());
			int imgY = (int) (normY * img.getHeight());
			// clamp to image bounds
			imgX = Math.max(0, Math.min(img.getWidth() - 1, imgX));
			imgY = Math.max(0, Math.min(img.getHeight() - 1, imgY));

			Color c = new Color(img.getRGB(imgX, imgY), true);

			// 4) paint the ball
			g2.setColor(c);
			g2.fillOval(screenX - diameterX / 2, screenY - diameterY / 2, diameterX, diameterY);
			sb.append(c.getRGB());
			sb.append(',');
		}
		out.print(sb);
		out.flush();
		out.close();

		// ── REPLACED LOOP END ──

	}

}
