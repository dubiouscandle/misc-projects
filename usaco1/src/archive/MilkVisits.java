package archive;


import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MilkVisits {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("milkvisits.in"));
			out = new PrintWriter(new File("milkvisits.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		int n = in.nextInt();
		int q = in.nextInt();

		String bits = in.next();

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] edges = (ArrayList<Integer>[]) new ArrayList[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < n - 1; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			edges[a].add(b);
			edges[b].add(a);
		}

		HashMap<Integer, ArrayList<Integer>> componentHash = new HashMap<>();
		ArrayList<ArrayList<Integer>> components = new ArrayList<>();
		boolean[] seen = new boolean[n];

		for (int i = 0; i < n; i++) {
			if (seen[i])
				continue;

			char bit = bits.charAt(i);
			ArrayList<Integer> component = new ArrayList<Integer>();
			Queue<Integer> queue = new LinkedList<>();
			queue.add(i);
			seen[i] = true;
			while (!queue.isEmpty()) {
				int cur = queue.poll();
				componentHash.put(cur, component);
				component.add(cur);
				for (int adj : edges[cur]) {
					if (seen[adj] || bits.charAt(adj) != bit)
						continue;

					queue.add(adj);
					seen[adj] = true;
				}
			}

			components.add(component);
		}

		for (int i = 0; i < q; i++) {
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;
			char bit = in.next().charAt(0);

			if (componentHash.get(a) != componentHash.get(b)) {
				out.print('1');
			} else {
				if (bit == bits.charAt(a))
					out.print('1');
				else
					out.print('0');
			}
		}

		in.close();
		out.flush();
		out.close();
	}

}
