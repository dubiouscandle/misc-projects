package ball_physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
	public static final double STEP_INTERVAL = 0.00025;
	public static final double BALL_RADIUS = 2;
	public static final double COEFFICIENT_OF_RESTITUTION = 0.997;
	public static final double WORLD_SIZE = 400;

	public static final long SEED = 198234709825890L;
	public static final String FILE_PATH = "resources/purpleman.png";

	public static final boolean RUN_FROM_CACHE = true;

	private static boolean repeatWorldOperation(Random random, World world, List<Color> colors) {
		boolean noBallsWereAdded = true;

		for (int i = 0; i < 30; i++) {
			int currentBallIndex = world.balls.size();

			Color color = colors == null ? Color.RED
					: (currentBallIndex >= colors.size() ? Color.BLACK : colors.get(currentBallIndex));

			boolean ballWasAdded = world.addBallIfUnobstructed(color,
					random.nextDouble(World.LOWER_BOUND + 5, World.LOWER_BOUND + 45),
					World.UPPER_BOUND_Y - 5 - random.nextDouble(0, 60), random.nextDouble(70, 72), random.nextDouble(-600, -550));

			if (ballWasAdded) {
				noBallsWereAdded = false;
				currentBallIndex++;
			}
		}

		world.step();

		return !noBallsWereAdded;
	}

	public static void main(String[] args) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(FILE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		img = resizeToSquare(img, (int) World.WORLD_WIDTH, 10);

		World worldCopy = new World();
		World world = new World(); // This will be used for the replay.

		WorldPanel panel = new WorldPanel(worldCopy, img);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setBackground(Color.PINK);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if (!RUN_FROM_CACHE) {
			int stepsSinceBallAdded = 0;
			int counter = 0;

			Random random = new Random(SEED);
			// First simulation to finalize ball positions.
			while (stepsSinceBallAdded < 500) {
				counter++;

				if (repeatWorldOperation(random, worldCopy, null)) {
					stepsSinceBallAdded = 0;
				}

				if (counter % 100 == 0) {
					panel.repaint();
				}

				stepsSinceBallAdded++;
			}

			List<Color> ballColors = new ArrayList<>();

			BufferedWriter writer;

			try {
				writer = new BufferedWriter(new FileWriter("resources/cache.txt"));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			for (int i = 0; i < worldCopy.balls.size(); i++) {
				Ball ball = worldCopy.balls.get(i);
				int imgX = (int) (ball.x * (img.getWidth() / (double) World.WORLD_WIDTH));
				int imgY = (int) ((World.WORLD_WIDTH - ball.y) * (img.getHeight() / (double) World.WORLD_WIDTH));

				// Ensure the coordinates are within the image bounds.
				imgX = Math.max(0, Math.min(img.getWidth() - 1, imgX));
				imgY = Math.max(0, Math.min(img.getHeight() - 1, imgY));

				Color ballColor = new Color(img.getRGB(imgX, imgY));
				ballColors.add(ballColor);

				ball.color = ballColor;
				try {
					writer.write(imgX + " " + imgY + " ");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			panel.img = null;
			panel.repaint();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return;
		} else {
			Scanner s = null;
			try {
				s = new Scanner(new File("resources/cache.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			List<Color> colors = new ArrayList<>();

			while (s.hasNext()) {
				colors.add(new Color(img.getRGB(s.nextInt(), s.nextInt())));
			}

			panel.world = world;
			panel.img = null;

			int frameRate = 240;
			double frameIntervalSeconds = 1.0 / frameRate;
			long frameIntervalNS = 1_000_000_000 / frameRate;
			long pTime = System.nanoTime();
			Random random = new Random(SEED);

			boolean running = true;

			while (running) {
				long currentTime = System.nanoTime();
				long elapsedTimeNS = currentTime - pTime;

				if (elapsedTimeNS >= frameIntervalNS) {
					world.elapsedTime += frameIntervalSeconds;

					boolean worldUpdated = false;

					while (world.elapsedTime >= World.STEP_INTERVAL) {
						repeatWorldOperation(random, world, colors);
						world.elapsedTime -= World.STEP_INTERVAL;
						worldUpdated = true;
					}

					if (worldUpdated) {
						panel.repaint();
					}
					pTime += frameIntervalNS;
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			s.close();

		}

	}

	public static BufferedImage resizeToSquare(BufferedImage originalImage, int size, int margin) {
		BufferedImage resizedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = resizedImage.createGraphics();

		g2d.drawImage(originalImage, margin, margin, size - margin * 2, size - margin * 2, null);
		g2d.dispose();

		return resizedImage;
	}
}
