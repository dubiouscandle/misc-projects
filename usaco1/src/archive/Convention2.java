package archive;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Convention2 {

	public static void main(String[] args) throws FileNotFoundException {
//		Scanner in = new Scanner(new File("convention2.in"));
//		PrintWriter out = new PrintWriter(new File("convention2.out"));
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int n = in.nextInt();

		Cow[] cows = new Cow[n];

		PriorityQueue<Long> times = new PriorityQueue<>();
		HashSet<Long> addTimes 
		for (int i = 0; i < n; i++) {
			cows[i] = new Cow(i, in.nextLong(), in.nextLong());
			times.add(cows[i].a);
		}

		Arrays.sort(cows);

		long t;
		times.add(0L);
		PriorityQueue<Cow> queue = new PriorityQueue<>();
		int cowIndex = 0;
		//points: add, remove
		while (true) {
			t = times.peek();

			if (t.peek) {
				if (cur.a + cur.t > t) {
					cur.
				}
			}
		}

		out.println();
		out.flush();
	}

	public static class Cow implements Comparable<Cow> {
		public long a, t;
		public int n;

		public Cow(int n, long a, long t) {
			super();
			this.n = n;
			this.a = a;
			this.t = t;
		}

		@Override
		public int compareTo(Convention2.Cow o) {
			if (t == o.t)
				return Long.compare(n, o.t);

			return Long.compare(a, o.a);
		}
	}
}
