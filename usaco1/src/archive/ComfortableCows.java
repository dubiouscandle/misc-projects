package archive;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class ComfortableCows {
	static HashSet<Point> cows = new HashSet<>(100_000);
	static HashSet<Point> comfortable = new HashSet<>(1_000_000);

	static int countAdj(int x, int y) {
		int c = 0;

		if (contains(x + 1, y))
			c++;
		if (contains(x - 1, y))
			c++;
		if (contains(x, y + 1))
			c++;
		if (contains(x, y - 1))
			c++;

		return c;
	}

	static Point getEmptyAdj(int x, int y) {
		if (!contains(x + 1, y))
			return new Point(x + 1, y);
		if (!contains(x - 1, y))
			return new Point(x - 1, y);
		if (!contains(x, y + 1))
			return new Point(x, y + 1);
		if (!contains(x, y - 1))
			return new Point(x, y - 1);

		return null;
	}

	static boolean contains(int x, int y) {
		return cows.contains(new Point(x, y));
	}

	static void add(int x, int y) {
		cows.add(new Point(x, y));
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();

			cows.add(new Point(x, y));
			fill(x, y);
			System.out.println(cows.size() - (i + 1));
		}
		
		
		System.out.println(cows);

		in.close();
	}

	private static void fill(int x, int y) {
		Stack<Point> stack = new Stack<>();

		if (countAdj(x, y) == 3) {
			stack.push(new Point(x, y));
		}
		if (contains(x + 1, y) && countAdj(x + 1, y) == 3) {
			stack.push(new Point(x + 1, y));
		}
		if (contains(x - 1, y) && countAdj(x - 1, y) == 3) {
			stack.push(new Point(x - 1, y));
		}
		if (contains(x, y + 1) && countAdj(x, y + 1) == 3) {
			stack.push(new Point(x, y + 1));
		}
		if (contains(x, y - 1) && countAdj(x, y - 1) == 3) {
			stack.push(new Point(x, y - 1));
		}

		while (!stack.isEmpty()) {
			Point cur = stack.pop();
			cows.add(cur);

			if (countAdj(cur.x, cur.y) == 3) {
				stack.push(getEmptyAdj(cur.x, cur.y));
			}
			if (contains(cur.x + 1, cur.y) && countAdj(cur.x + 1, cur.y) == 3) {
				stack.push(getEmptyAdj(cur.x + 1, cur.y));
			}
			if (contains(cur.x - 1, cur.y) && countAdj(cur.x - 1, cur.y) == 3) {
				stack.push(getEmptyAdj(cur.x - 1, cur.y));
			}
			if (contains(cur.x, cur.y + 1) && countAdj(cur.x, cur.y + 1) == 3) {
				stack.push(getEmptyAdj(cur.x, cur.y + 1));
			}
			if (contains(cur.x, cur.y - 1) && countAdj(cur.x, cur.y - 1) == 3) {
				stack.push(getEmptyAdj(cur.x, cur.y - 1));
			}

		}
	}

	static class Point {

		final int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}

		@Override
		public int hashCode() {
			return x * 31 + y;
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
