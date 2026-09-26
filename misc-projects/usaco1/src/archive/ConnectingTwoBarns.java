package archive;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ConnectingTwoBarns {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int t = in.nextInt();

		for (int i = 0; i < t; i++) {
			solution(in);
		}

	}

	private static void solution(Scanner in) {
		int n = in.nextInt();
		int m = in.nextInt();

		ArrayList<Integer>[] edges = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<>();
		}

		for (int i = 0; i < m; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			edges[a].add(b);
			edges[b].add(a);
		}

		boolean[] seen = new boolean[n];
		ArrayList<ArrayList<Integer>> components = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			if (seen[i])
				continue;

			ArrayList<Integer> component = new ArrayList<>();
			components.add(component);
			Queue<Integer> queue = new LinkedList<>();
			seen[i] = true;
			queue.add(i);
			while (!queue.isEmpty()) {
				int cur = queue.poll();
				component.add(cur);

				for (int adj : edges[cur]) {
					if (seen[adj])
						continue;

					seen[adj] = true;
					queue.add(adj);
				}
			}
		}

		ArrayList<Integer> begin = null, end = null;

		for (ArrayList<Integer> c : components) {
			if (c.contains(0)) {
				begin = c;
			}
		}
		for (ArrayList<Integer> c : components) {
			if (c.contains(n - 1)) {
				end = c;
			}
		}

		if (begin == end) {
			System.out.println(0);
			return;
		}

		components.remove(begin);
		components.remove(end);

		begin.sort(Integer::compare);
		end.sort(Integer::compare);

		long[] distsBegin = getDistArr(begin, n);
		long[] distsEnd = getDistArr(end, n);

//		System.out.println(begin);
//		System.out.println(end);
//		System.out.println(components);

//		System.out.println(Arrays.toString(distsBegin));

		long min = Long.MAX_VALUE;

		for (ArrayList<Integer> component : components) {
			long dist = 0;

			long subMinBegin = Long.MAX_VALUE;
			for (int node : component) {
				if (distsBegin[node] < subMinBegin) {
					subMinBegin = distsBegin[node];
				}
			}

			long subMinEnd = Long.MAX_VALUE;
			for (int node : component) {
				if (distsEnd[node] < subMinEnd) {
					subMinEnd = distsEnd[node];
				}
			}

			dist = subMinEnd + subMinBegin;
//			System.out.println(component + " " + dist);
			if (dist < min) {
				min = dist;
			}
		}

		long lastDist = Long.MAX_VALUE;
		for (int node : begin) {
			if (distsEnd[node] < lastDist) {
				lastDist = distsEnd[node];
			}
		}

		System.out.println(Math.min(min, lastDist));
	}

	private static long[] getDistArr(ArrayList<Integer> arr, int n) {
		long[] dists = new long[n];

		for (int j = 0; j <= arr.get(0); j++) {
			dists[j] = (long) (arr.get(0) - j) * (long) (arr.get(0) - j);
		}
		for (int j = arr.get(arr.size() - 1); j <= n - 1; j++) {
			dists[j] = (long) (arr.get(arr.size() - 1) - j) * (long) (arr.get(arr.size() - 1) - j);
		}

		for (int i = 0; i < arr.size() - 1; i++) {
			int cur = arr.get(i);
			int next = arr.get(i + 1);

			for (int j = cur; j <= next; j++) {
				long dx = Math.min(Math.abs(cur - j), Math.abs(next - j));
				dists[j] = (long) dx * dx;
			}
		}

		return dists;
	}

}
/*
 * 
 * 1 16 11 1 3 3 15 2 5 5 13 13 7 5 6 2 6 11 10 10 9 9 11 8 14
 */
