package verlet;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = -4811282199848640818L;
	private Solver solver;

	public RenderPanel(Solver solver) {
		setBackground(Color.BLACK);
		this.solver = solver;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		float scale = getWidth() < getHeight() ? getWidth() : getHeight();

		g.setColor(Color.BLUE);
		for (Particle particle : solver.particles) {
			int x = (int) (particle.x * scale);
			int y = getHeight() - (int) (particle.y * scale);
			int r = (int) (Solver.RADIUS * scale);
			g.fillOval(x - r, y - r, 2 * r, 2 * r);
		}
	}
}
