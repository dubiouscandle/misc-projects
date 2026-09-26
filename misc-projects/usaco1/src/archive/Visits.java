package archive;
import java.util.Scanner;

public class Visits {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		int[] edges = new int[n];
		int[] inDegrees = new int[n];
		long[] weights = new long[n];

		long c = 0;
		for (int i = 0; i < n; i++) {
			int dest = in.nextInt() - 1;
			edges[i] = dest;
			inDegrees[dest]++;
			weights[i] = in.nextLong();
			c += weights[i];
		}

		boolean[] seen = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (seen[i] || inDegrees[i] > 0)
				continue;

			int cur = i;

			while (!seen[cur]) {
				if (inDegrees[cur] != 0) {
					break;
				}

				seen[cur] = true;

				cur = edges[cur];
				inDegrees[cur]--;
			}
		}
//
//		for (int i = 0; i < n; i++) {
//			System.out.println(i + " " + seen[i]);
//		}
		for (int i = 0; i < n; i++) {
			if (seen[i])
				continue;

			int cur = i;
			long min = Long.MAX_VALUE;

			while (!seen[cur]) {
				seen[cur] = true;

				if (weights[cur] < min)
					min = weights[cur];

				cur = edges[cur];
			}

			c -= min;
		}

		System.out.println(c);
	}

	/*
	 * 12 2 10 5 10 4 20 5 30 6 10 4 10 8 10 9 10 3 10 11 10 10 10 11 10
	 */
}
