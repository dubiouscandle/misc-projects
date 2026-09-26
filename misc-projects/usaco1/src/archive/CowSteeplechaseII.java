package archive;
import java.awt.Point;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class CowSteeplechaseII {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("cowjump.in"));
			out = new PrintWriter(new File("cowjump.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		PriorityQueue<Event> eventQueue = new PriorityQueue<>();

		int n = in.nextInt();
		Line[] lines = new Line[n];
		HashMap<Line, Integer> lineIndexMap = new HashMap<>();
		for (int i = 0; i < n; i++) {
			lines[i] = new Line(in.nextLong(), in.nextLong(), in.nextLong(), in.nextLong());
			eventQueue.add(new Event(lines[i].x1, 'A', lines[i]));
			eventQueue.add(new Event(lines[i].x2, 'R', lines[i]));

			lineIndexMap.put(lines[i], i);
		}
		int[] intersectionCount = new int[n];

		HashSet<Line> active = new HashSet<>();
		while (!eventQueue.isEmpty()) {
			Event cur = eventQueue.poll();

			if (cur.type == 'A') {
				for (Line line : active) {
					if (intersects(cur.line, line)) {
						intersectionCount[lineIndexMap.get(cur.line)]++;
						intersectionCount[lineIndexMap.get(line)]++;
					}
				}
				active.add(cur.line);
			} else if (cur.type == 'R') {
				active.remove(cur.line);
			}
		}

		for (int i = 0; i < intersectionCount.length; i++) {
			if (intersectionCount[i] >= 1) {
				out.println(i + 1);
			}
		}
		System.out.println(Arrays.toString(intersectionCount));

		in.close();
		out.flush();
		out.close();
	}

	public static class Point {
		long x, y;

		public Point(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static boolean intersects(Line a, Line b) {
		Point p1 = new Point(a.x1, a.y1), q1 = new Point(a.x2, a.y2);
		Point p2 = new Point(b.x1, b.y1), q2 = new Point(b.x2, b.y2);

		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4) {
			return true;
		}

		// Special cases
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false;
	}

	private static int orientation(Point p, Point q, Point r) {
		long val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
		if (val == 0)
			return 0;

		return (val > 0) ? 1 : 2;
	}

	// Function to check if point r lies on segment pq
	private static boolean onSegment(Point p, Point r, Point q) {
		return r.x <= Math.max(p.x, q.x) && r.x >= Math.min(p.x, q.x) && r.y <= Math.max(p.y, q.y)
				&& r.y >= Math.min(p.y, q.y);
	}

	public static class Line {
		public long x1, y1, x2, y2;

		public Line(long x1, long y1, long x2, long y2) {
			super();
			if (x1 > x2) {
				long temp = x1;
				x1 = x2;
				x2 = temp;
			}

			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

	public static class Event implements Comparable<Event> {
		public char type;
		public long x;
		public Line line;

		public Event(long x, char type, CowSteeplechaseII.Line line) {
			super();
			this.x = x;
			this.type = type;
			this.line = line;
		}

		@Override
		public int compareTo(CowSteeplechaseII.Event o) {
			if (x == o.x) {
				return -Integer.compare(type, o.type);
			}
			
			return Long.compare(x, o.x);
		}
	}
}
