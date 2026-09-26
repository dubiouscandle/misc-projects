package archive;
import java.awt.Point;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class WormholeSort {
	static int n, m;
	static int[] arr;
	static ArrayList<Edge>[] edges;
	static Point[] pairs;

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("wormsort.in"));
			out = new PrintWriter("wormsort.out");
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}
		n = in.nextInt();
		m = in.nextInt();
		edges = new ArrayList[n];
		arr = new int[n];
		pairs = new Point[n];
		for (int i = 0; i < n; i++) {
			arr[in.nextInt() - 1] = i;

			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < n; i++) {
			pairs[i] = new Point(i, arr[i]);
		}

		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			long w = in.nextLong();

			edges[a].add(new Edge(b, w));
			edges[b].add(new Edge(a, w));
		}

		if (works(Long.MAX_VALUE)) {
			out.print(-1);
			out.flush();
			return;
		}

		long max = 2_000_000_000;
		long min = 0;

		while (min < max) {
			long mid = (min + max + 1) / 2;

			boolean works = works(mid);

			if (works) {
				min = mid;
			} else {
				max = mid - 1;
			}
		}

		out.print(min);
		in.close();
		out.flush();
		out.close();

	}

	static boolean works(long w) {
		boolean[] seen = new boolean[n];

		ArrayList<ArrayList<Integer>> components = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			if (seen[i])
				continue;

			ArrayList<Integer> component = new ArrayList<>();
			components.add(component);
			Queue<Integer> queue = new LinkedList<>();
			queue.add(i);
			seen[i] = true;
			while (queue.size() > 0) {
				int cur = queue.poll();
				component.add(cur);

				for (Edge edge : edges[cur]) {
					if (seen[edge.next] || edge.weight < w)
						continue;

					queue.add(edge.next);
					seen[edge.next] = true;
				}
			}
		}

		HashMap<Integer, ArrayList<Integer>> componentMap = new HashMap<>();
		for (ArrayList<Integer> component : components) {
			for (int node : component) {
				componentMap.put(node, component);
			}
		}

		for (Point pair : pairs) {
			if (componentMap.get(pair.x) != componentMap.get(pair.y)) {
				return false;
			}
		}

		return true;

	}

	static class Edge {
		int next;
		long weight;

		public Edge(int next, long weight) {
			super();
			this.next = next;
			this.weight = weight;
		}
	}
}
