package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SwapitySwapitySwap {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("swap.in"));
			out = new PrintWriter("swap.out");
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		int n = in.nextInt();
		int m = in.nextInt();
		int k = in.nextInt();

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i;
		}

		for (int i = 0; i < m; i++) {
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;

			while (l < r) {
				int temp = arr[l];
				arr[l] = arr[r];
				arr[r] = temp;

				l++;
				r--;
			}
		}

		HashMap<Integer, ArrayList<Integer>> cycleMap = new HashMap<>();
		HashMap<Integer, Integer> cycleIndexMap = new HashMap<>();

		boolean[] seen = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (seen[i])
				continue;

			ArrayList<Integer> cycle = new ArrayList<>();
			int j = 0;
			int cur = i;
			while (true) {
				cycleMap.put(cur, cycle);
				cycleIndexMap.put(cur, j);
				cycle.add(cur);
				j++;
				seen[cur] = true;
				cur = arr[cur];

				if (seen[cur]) {
					break;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			ArrayList<Integer> cycle = cycleMap.get(i);
			int index = cycleIndexMap.get(i);
			int dest = cycle.get((k + index) % cycle.size());

			out.println(dest + 1);
		}
		out.flush();
		out.close();
		in.close();

	}

}
