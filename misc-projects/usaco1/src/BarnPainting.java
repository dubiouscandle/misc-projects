import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BarnPainting {
	static int n, k;
	static int[] colors;
	static ArrayList<Integer>[] biEdges;
	static ArrayList<Integer>[] edges;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("barnpainting.in"));
			out = new PrintWriter(new File("barnpainting.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		int n = in.nextInt();
		int k = in.nextInt();
		colors = new int[n];
		biEdges = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			biEdges[i] = new ArrayList<>();
		}

		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			biEdges[a].add(b);
			biEdges[b].add(a);
		}

		for (int i = 0; i < k; i++) {
			colors[in.nextInt() - 1] = in.nextInt();
		}

		edges = new ArrayList[n];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = new ArrayList<>();
		}

		int root = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(root);
		boolean[] seen = new boolean[n];
		seen[root] = true;
		while (!queue.isEmpty()) {
			int cur = queue.poll();

			for (int adjacent : biEdges[cur]) {
				if (seen[adjacent])
					continue;

				edges[cur].add(adjacent);
				seen[adjacent] = true;
				queue.add(adjacent);
			}
		}

		System.out.println(countSubtree(0));

		in.close();
		out.flush();
		out.close();
	}

	private static long countSubtree(int parent) {
//		if (color[start] != 0) {
//
//		}

		long ways = 1;

		for (int descendent : edges[parent]) {
			ways *= countSubtree(descendent);
		}

		return ways;
	}

}
