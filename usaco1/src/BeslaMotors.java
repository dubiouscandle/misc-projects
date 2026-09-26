import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BeslaMotors {
	static int n;
	static int m;
	static int c;
	static long r;
	static int k;
	static int[] counts;

	static ArrayList<Edge>[] edges;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		n = in.nextInt();
		m = in.nextInt();
		c = in.nextInt();
		r = in.nextLong();
		k = in.nextInt();

		edges = new ArrayList[n + 1];
		
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new ArrayList<>();
		}

		for (int i = 0; i < m; i++) {
			int a = in.nextInt();
			int b = in.nextInt();
			int len = in.nextInt();

			edges[a].add(new Edge(b, len));
			edges[b].add(new Edge(a, len));
		}

		counts =  new int[n + 1];;
		
		for(int i = 1; i <= c; i++) {
			count(i);
		}
	}

	private static void count(int start) {
		
	}

	public static class Node implements Comparable<Node> {
		public int num;
		public long dist;

		public Node(int num, long time) {
			super();
			this.num = num;
			this.dist = time;
		}

		@Override
		public int compareTo(BeslaMotors.Node o) {
			return Long.compare(dist, o.dist);
		}

	}

	public static class Edge {
		public int to;
		public long len;

		public Edge(int to, long len) {
			super();
			this.to = to;
			this.len = len;
		}
	}
}
