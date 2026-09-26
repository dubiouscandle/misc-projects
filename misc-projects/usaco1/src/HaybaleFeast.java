import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class HaybaleFeast {
	static int n;
	static long m;
	static Pair[] arr;

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("hayfeast.in"));
			out = new PrintWriter(new File("hayfeast.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		n = in.nextInt();
		m = in.nextLong();

		arr = new Pair[n];

		for (int i = 0; i < n; i++) {
			arr[i] = new Pair(in.nextLong(), in.nextLong());
		}

		long max = 1_000_000_000_000_000_000L;
		long min = 0;

		while (min < max) {
			long mid = (min + max) / 2;

			boolean works = works(mid);

			if (works) {
				max = mid;
			} else {
				min = mid + 1;
			}
		}

		out.print(min);
		in.close();
		out.flush();
		out.close();
	}

	static boolean works(long s) {
		long sum = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i].s <= s) {
				sum += arr[i].t;
				if (sum >= m) {
					return true;
				}
			} else {
				sum = 0;
			}
		}

		return false;
	}

	static class Pair implements Comparable<Pair> {
		long t, s;

		public Pair(long t, long s) {
			super();
			this.t = t;
			this.s = s;
		}

		@Override
		public int compareTo(HaybaleFeast.Pair o) {
			return Long.compare(s, o.s);
		}

		@Override
		public String toString() {
			return "[t=" + t + ", s=" + s + "]";
		}

		Pair copy() {
			return new Pair(t, s);
		}
	}
}
