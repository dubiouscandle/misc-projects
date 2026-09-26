package verlet;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Solver solver = new Solver();
		Random random = new Random();

		JFrame frame = new JFrame();
		RenderPanel panel = new RenderPanel(solver);
		panel.setPreferredSize(new Dimension(800, 800));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final long frameIntervalNS = (long) ((double) Solver.DELTA_T * 1_000_000_000.0);
		int frameCount = 0;
		long delta = 0;
		long pTime = System.nanoTime();
		while (true) {
			long curTime = System.nanoTime();
			delta += curTime - pTime;
			pTime = curTime;

			while (delta > frameIntervalNS) {
				if (frameCount % 1 == 0 && frameCount < 3000) {
					solver.particles
							.add(new Particle(random.nextFloat(Solver.RADIUS, 1 - Solver.RADIUS), 1 - Solver.RADIUS));
				}
				delta -= frameIntervalNS;
				solver.step();
				frameCount++;
			}

			panel.repaint();

			Thread.sleep(10);
		}
	}
}
