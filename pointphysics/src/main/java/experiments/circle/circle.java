package experiments.circle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dubiouscandle.pointphysics.Body;
import com.dubiouscandle.pointphysics.BodyList;
import com.dubiouscandle.pointphysics.Solver;
import com.dubiouscandle.pointphysics.mechanics.core.BallConstraint;
import com.dubiouscandle.pointphysics.mechanics.core.CircularWorld;
import com.dubiouscandle.pointphysics.mechanics.core.ConstantForce;
import com.dubiouscandle.pointphysics.mechanics.core.LinearDamping;

public class circle {
	public static void main(String[] args) throws FileNotFoundException {
		float frameInterval = 1f / 60;

		BodyList bodyList = new BodyList();
		Random random = new Random(4);
		Solver solver = new Solver(bodyList, frameInterval);

		solver.addConstraint(new BallConstraint(bodyList));

		solver.addConstraint(new CircularWorld(bodyList, 0, 0, 300));
		solver.addForce(new ConstantForce(bodyList, 0, -100));
		solver.addForce(new LinearDamping(bodyList, 0.1f));

		JPanel panel = new RenderPanel(bodyList);

		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		PrintWriter out = new PrintWriter("src/main/java/experiments/circle/poses.txt");
		long t0 = System.nanoTime() - 10_000_000_000L;
		int frameCount = 0;

		while (true) {
			int requiredFrames = (int) ((System.nanoTime() - t0) / 1_000_000_000.0 / frameInterval);

			while (frameCount < requiredFrames) {
				if (frameCount % 1 == 0 && frameCount < 1300) {
					for (int i = 0; i < 3; i++) {
						float r = random.nextFloat(3, 6);
						Body b = new Body(r, r * r);
						bodyList.add(b);

						b.x = random.nextFloat(-30, 30);
						b.y = random.nextFloat(270, 290);

						while (b.x * b.x + b.y * b.y > 300 * 300) {
							b.x = random.nextFloat(-300, 300);
							b.y = random.nextFloat(290, 300);
						}

						b.zeroVelocity();
					}
				}

				solver.step(16);
				StringBuilder xs = new StringBuilder();
				StringBuilder ys = new StringBuilder();
				StringBuilder radii = new StringBuilder();
				xs.append("[");
				ys.append("[");
				radii.append("[");
				for (Body body : bodyList) {
					xs.append(String.format("%1.3f", body.x)).append(',');
					ys.append(String.format("%1.3f", body.y)).append(',');
					radii.append(String.format("%1.3f", body.radius)).append(',');
				}
				xs.append("],");
				ys.append("],");
				radii.append("],");

				out.println(xs);
				out.println(ys);
				out.println(radii);

				if (frameCount % 100 == 0)
					System.out.println(frameCount);

				frameCount++;
			}

			panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
			if (frameCount > 1300 + 100) {
				break;
			}

		}

		out.flush();
		out.close();
		System.out.println("ASIOD");
		return;
	}

}
