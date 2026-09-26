package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BarnTree {
	static HashSet<Integer>[] descendents;
	static long sum = 0;
	static long numEach;
	static int n;
	static ArrayList<Integer>[] edges;
	static ArrayList<int[]> edgeList = new ArrayList<>();
	static long[] vals = new long[n];

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		n = in.nextInt();

		vals = new long[n];
		numDescendentsSum = new int[n];
		valsPrefixSum = new long[n];
		for (int i = 0; i < n; i++) {
			numDescendentsSum[i] = -1;
			valsPrefixSum[i] = -1;
			vals[i] = in.nextLong();
			sum += vals[i];
		}
		numEach = sum / n;

		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			edges[a].add(b);
			edges[b].add(a);

			edgeList.add(new int[] { a, b });
		}

		descendents = new HashSet[n];
		for (int i = 0; i < descendents.length; i++) {
			descendents[i] = new HashSet<>();
		}
		Queue<Integer> queue = new LinkedList<>();
		boolean[] seen = new boolean[n];
		queue.add(0);
		seen[0] = true;
		while (queue.size() > 0) {
			int cur = queue.poll();
			for (int adj : edges[cur]) {
				if (seen[adj])
					continue;

				seen[adj] = true;
				queue.add(adj);
				descendents[cur].add(adj);
			}
		}

		Queue<int[]> requiredEdges = new LinkedList<>();

		for (int i = 0; i < edgeList.size(); i++) {
			int a = edgeList.get(i)[0];
			int b = edgeList.get(i)[1];

			long requiredA = subTreeCounts(a, b)[0] * numEach;
			long requiredB = subTreeCounts(a, b)[1] * numEach;

			long countA = subTreeSums(a, b)[0];
			long countB = subTreeSums(a, b)[1];

			if (requiredA != countA && requiredB != countB) {
				requiredEdges.add(new int[] { a, b });
			}
		}

		System.out.println(requiredEdges.size());
		while (requiredEdges.size() > 0) {
			int[] edge = requiredEdges.poll();

			int a = edge[0];
			int b = edge[1];

			long requiredA = subTreeCounts(a, b)[0] * numEach;
			long requiredB = subTreeCounts(a, b)[1] * numEach;

			long countA = subTreeSums(a, b)[0];
			long countB = subTreeSums(a, b)[1];

			System.out.println(a + " " + b);
			if (countB > requiredB && vals[b] >= (countB - requiredB)) {
				long diff = countB - requiredB;
				vals[b] -= diff;
				vals[a] += diff;
				System.out.println((b) + " " + (a) + " " + diff);
			} else if (countA > requiredA && vals[a] >= (countA - requiredA)) {
				System.out.println(a + " " + countA + " " + requiredA);
				long diff = countA - requiredA;
				vals[a] -= diff;
				vals[b] += diff;
				System.out.println((a) + " E" + (b) + " " + diff);
			} else {
				requiredEdges.add(edge);
			}

			System.out.println(Arrays.toString(vals));

		}

		System.out.println(Arrays.toString(vals));
	}

	static int[] subTreeCounts(int a, int b) {
		if (descendents[a].contains(b)) {
			// b is descendant of a
			return new int[] { n - getNumDescendents(b), getNumDescendents(b) };
		} else if (descendents[b].contains(a)) {
			// a descends b
			return new int[] { getNumDescendents(a), n - getNumDescendents(a) };
		}

		return null;
	}

	static int[] numDescendentsSum;
	static long[] valsPrefixSum;

	static long[] subTreeSums(int a, int b) {
		if (descendents[a].contains(b)) {
			// b is descendant of a
			return new long[] { sum - getPrefixSum(b), getPrefixSum(b) };
		} else if (descendents[b].contains(a)) {
			// a descends b
			return new long[] { getPrefixSum(a), sum - getPrefixSum(a) };
		}

		return null;
	}

	static long getPrefixSum(int node) {
		if (valsPrefixSum[node] != -1)
			return valsPrefixSum[node];

		if (descendents[node].size() == 0) {// is leaf
			valsPrefixSum[node] = vals[node];
			return vals[node];
		}

		long sum = vals[node];
		for (int descendent : descendents[node]) {
			sum += getPrefixSum(descendent);
		}

		valsPrefixSum[node] = sum;
		return sum;
	}

	static int getNumDescendents(int node) {
		if (numDescendentsSum[node] != -1)
			return numDescendentsSum[node];

		if (descendents[node].size() == 0) {// is leaf
			numDescendentsSum[node] = 1;
			return numDescendentsSum[node];
		}

		int sum = 1;
		for (int descendent : descendents[node]) {
			sum += getNumDescendents(descendent);
		}

		valsPrefixSum[node] = sum;
		return sum;
	}

}
