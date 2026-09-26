package silver_usopen2024;

import java.awt.Point;
import java.util.Scanner;

public class PaintingFencePosts {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numCows = s.nextInt();
		int numPosts = s.nextInt();

		Point[] posts = new Point[numPosts];

		for (int i = 0; i < numPosts; i++) {
			posts[i] = new Point(s.nextInt(), s.nextInt());
		}

		Point[] cowPointsA = new Point[numCows];
		Point[] cowPointsB = new Point[numCows];

		for (int i = 0; i < numCows; i++) {
			cowPointsA[i] = new Point(s.nextInt(), s.nextInt());
			cowPointsB[i] = new Point(s.nextInt(), s.nextInt());
		}

		s.close();

		for (int i = 0; i < numCows; i++) {

		}
	}

}
