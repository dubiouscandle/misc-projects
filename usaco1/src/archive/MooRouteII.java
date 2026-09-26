package archive;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MooRouteII {
	static int n, m;
	static Queue<Edge>[] edges;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		m = in.nextInt();

		edges = new Queue[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new LinkedList<>();
		}
		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int t1 = in.nextInt();
			int b = in.nextInt() - 1;
			int t2 = in.nextInt();

			edges[a].add(new Edge(b, t1, t2));
		}

		for (int i = 0; i < n; i++) {
			long a = in.nextLong();
			for (Edge e : edges[i]) {
				e.t1 -= a;
			}
		}

		long[] earliestTimes = new long[n];
		Arrays.fill(earliestTimes, Long.MAX_VALUE);

		Queue<Integer> queue = new LinkedList<>();
		queue.add(0);
		earliestTimes[0] = Long.MIN_VALUE;

		while (queue.size() > 0) {
			int cur = queue.poll();
			Iterator<Edge> iter = edges[cur].iterator();
			while (iter.hasNext()) {
				Edge edge = iter.next();
				if (edge.t1 < earliestTimes[cur]) { // cant use
					break;
				}
				if (edge.t2 < earliestTimes[edge.next]) {
					queue.add(edge.next);
					earliestTimes[edge.next] = edge.t2;
				}
				iter.remove();
			}
		}

		for (int i = 0; i < n; i++) {
			if (earliestTimes[i] == Long.MIN_VALUE)
				System.out.println(0);
			else if (earliestTimes[i] == Long.MAX_VALUE)
				System.out.println(-1);
			else
				System.out.println(earliestTimes[i]);
		}
	}

	static class Edge implements Comparable<Edge> {
		int next;
		long t1, t2;

		public Edge(int next, long t1, long t2) {
			super();
			this.next = next;
			this.t1 = t1;
			this.t2 = t2;
		}

		@Override
		public String toString() {
			return "Edge [next=" + next + ", t1=" + t1 + ", t2=" + t2 + "]";
		}

		@Override
		public int compareTo(MooRouteII.Edge o) {
			return -Long.compare(t1, o.t1);
		}
	}
}
