package archive;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FieldDay {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int c = in.nextInt();
		int n = in.nextInt();

		int[] arr = new int[n];

		for (int i = 0; i < n; i++) {
			String line = in.next();

			for (int j = 0; j < c; j++) {
				arr[i] = arr[i] << 1;

				if (line.charAt(j) == 'G')
					arr[i]++;
			}
		}

		boolean[] seen = new boolean[1 << c];
		int[] graph = new int[1 << c];

		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			int a = arr[i];

			graph[a] = 0;
			seen[a] = true;
			queue.add(a);
		}

		while (!queue.isEmpty()) {
			int cur = queue.poll().intValue();
			int dist = graph[cur];

			for (int i = 0; i < c; i++) {
				int mask = 1 << i;
				int adj = mask ^ cur;

				if (seen[adj])
					continue;

				seen[adj] = true;
				graph[adj] = dist + 1;
				queue.add(adj);
			}
		}

		for (int i = 0; i < n; i++) {
			System.out.println(c - graph[arr[i] ^ ((1 << c) - 1)]);
		}

//		print(arr, c);
	}

	public static void print(int[] arr, int len) {
		for (int x : arr) {
			for (int i = len - 1; i >= 0; i--) {
				System.out.print(((x >> i & 1) == 1 ? 1 : 0));
			}
			System.out.print(" ");
		}
		System.out.println();
	}

}
