package main;

import javax.swing.JFrame;

import physics.Solver;

public class Main {

	public static void main(String[] args) {
		Solver solver = new Solver();

		Renderer renderer = new Renderer();

		JFrame frame = new SingleElementFrame(16.0 / 9, renderer);

		frame.setVisible(true);

		long delta = 0;
		int frameRate = 500;
		long frameIntervalNS = 1_000_000_000 / frameRate;
		double frameIntervalSeconds = 1.0 / frameRate;

		long pTime = System.nanoTime();
		long currentTime = System.nanoTime();

		while (true) {
			delta += currentTime - pTime;
			
			while (delta > frameIntervalNS) {
				delta -= frameIntervalNS;

				solver.update(frameIntervalSeconds);
				renderer.repaint();

			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			pTime = currentTime;
			currentTime = System.nanoTime();
		}
	}

}
