import java.awt.Point;
import java.util.HashSet;

public class CounterAttack {
	public static void main(String[] args) {
		int[][] coordinates = {
	             { 2, 3 }, 
	             { 422, 343 }, 
	             { 29, 4314 }, 
	             { 142, 54 }, 
	             { 432, 765 }, 
	             { 43, 543 },
				{ 33, 566 }, 
				{ 1023, 20 } 
			};
		int len = coordinates.length;
		int startX = coordinates[0][0];
		int startY = coordinates[0][1];
		int endX = coordinates[len - 1][0];
		int endY = coordinates[len - 1][1];

		HashSet<Point> points = new HashSet<>();

		for (int i = 1; i < len - 1; i++) {
			points.add(new Point(coordinates[i][0], coordinates[i][1]));
			System.out.println("a = (" + coordinates[i][0] + ", " + coordinates[i][1] + ")");
		}

		int rise = endY - startY;
		int run = endX - startX;

		int posGcd = gcd(Math.abs(rise), Math.abs(run));
		rise /= posGcd;
		run /= posGcd;

		int x = startX;
		int y = startY;
		int count = 0;
		while (x != endX || y != endY) {
			if (points.contains(new Point(x, y))) {
				count++;
			}

			x += run;
			y += rise;
		}

		System.out.println(Math.hypot(startX - endX, startY - endY) * Math.pow(2, count));
	}

	public static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}

		return gcd(b, a % b);
	}
}
