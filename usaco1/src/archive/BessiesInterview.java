package archive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class BessiesInterview {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int k = in.nextInt();

		Queue<Long> queue = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			queue.add(in.nextLong());
		}

		PriorityQueue<Farmer> farmers = new PriorityQueue<>();
		for (int i = 0; i < k; i++) {
			farmers.add(new Farmer(i, 0));
		}

		in.close();

		ArrayList<ArrayList<Farmer>> splits = new ArrayList<>();

		while (!queue.isEmpty()) {
			long first = farmers.peek().t;

			ArrayList<Farmer> split = new ArrayList<>();

			while (!queue.isEmpty() && farmers.peek().t == first) {
				Farmer cur = farmers.poll();
				split.add(cur);
				cur.t += queue.poll();

				farmers.add(cur);
			}

			if (split.size() >= 2) {
				splits.add(split);
			}

		}

		splits.remove(0);

		HashSet<Farmer> possible = new HashSet<>();
		possible.add(farmers.peek());

		for (int i = splits.size() - 1; i >= 0; i--) {
			ArrayList<Farmer> split = splits.get(i);

			boolean contains = false;

			for (Farmer f : split) {
				if (possible.contains(f))
					contains = true;
			}

			if (contains)
				possible.addAll(split);
		}

		ArrayList<Farmer> farmersArr = new ArrayList<>(farmers);
		farmersArr.sort((a, b) -> Long.compare(a.n, b.n));

//		System.out.println(splits);
		System.out.println(farmers.peek().t);
		for (Farmer f : farmersArr) {
			if (possible.contains(f)) {
				System.out.print("1");
			} else {
				System.out.print("0");
			}
		}
	}

	public static class Farmer implements Comparable<Farmer> {
		public long n, t;

		public Farmer(long n, long t) {
			super();
			this.n = n;
			this.t = t;
		}

		@Override
		public String toString() {
			return "" + n;
		}

		@Override
		public int compareTo(BessiesInterview.Farmer o) {
			if (t == o.t)
				return Long.compare(n, o.n);
			return Long.compare(t, o.t);
		}
		/*
		 * 12 5 1 1 2 2 3 2 1 5 5 5 7 6
		 */

	}

}
