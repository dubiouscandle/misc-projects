package turtle_drawing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Renderer extends JPanel {
	private static final long serialVersionUID = -1043192884114425538L;
	private BufferedImage img;

	public Renderer(BufferedImage img) {
		this.img = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
}
