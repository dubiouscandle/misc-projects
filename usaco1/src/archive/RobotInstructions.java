package archive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Scanner;

public class RobotInstructions {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		long xg = in.nextLong();
		long yg = in.nextLong();

		long[] xs = new long[n];
		long[] ys = new long[n];

		for (int i = 0; i < n; i++) {
			xs[i] = in.nextLong();
			ys[i] = in.nextLong();
		}

		in.close();

		if (n <= 20) {
			long[] c = new long[n + 1];
			for (long i = 0; i < (1 << n); i++) {
				long x = 0;
				long y = 0;

				int order = 0;
				for (int j = 0; j < n; j++) {
					if (((i >> j) & 1) == 1) {
						x += xs[j];
						y += ys[j];
						order++;
					}
				}

				if (x == xg && y == yg)
					c[order]++;
			}

			for (int i = 1; i < c.length; i++)
				System.out.println(c[i]);

			return;
		}

		HashMap<Point, ArrayList<Integer>> complements = new HashMap<>();

		for (long i = 0; i < (1 << 20); i++) {
			long x = 0;
			long y = 0;

			int order = 0;
			for (int j = 0; j < n; j++) {
				if (((i >> j) & 1) == 1) {
					x += xs[j];
					y += ys[j];
					order++;
				}
			}

			Point complement = new Point(xg - x, yg - y);
			if (!complements.containsKey(complement))
				complements.put(complement, new ArrayList<>());

			complements.get(complement).add(order);
		}
		long[] c = new long[n + 1];
		for (long i = 0; i < (1 << 20); i++) {
			long x = 0;
			long y = 0;

			int order = 0;
			for (int j = 0; j < n - 20; j++) {
				if (((i >> j) & 1) == 1) {
					x += xs[j + 20];
					y += ys[j + 20];
					order++;
				}
			}

			Point p = new Point(x, y);

			if (complements.containsKey(p)) {
				for (Integer moves : complements.get(p)) {
					c[moves + order]++;
				}
			}
		}

		for (int i = 1; i < c.length; i++)
			System.out.println(c[i]);

		return;
	}

	public static class Point {
		public final long x, y;

		public Point(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}
	}

}
