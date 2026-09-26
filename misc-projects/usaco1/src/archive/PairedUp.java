package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class PairedUp {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("pairup.in"));
			out = new PrintWriter(new File("pairup.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		int n = in.nextInt();

		Pair[] pairs = new Pair[n];
		for (int i = 0; i < n; i++) {
			pairs[i] = new Pair(in.nextLong(), in.nextLong());
		}
		
		Arrays.sort(pairs, (a, b) -> Long.compare(a.o, b.o));

		int i = 0;
		int j = n - 1;
		long max = Long.MIN_VALUE;

		while (i < j) {
			Pair l = pairs[i];
			Pair r = pairs[j];

			if (l.n > r.n) {
				l.n -= r.n;

				j--;
				long t = l.o + r.o;

				if (t > max)
					max = t;
			} else if (r.n > l.n) {
				r.n -= l.n;

				i++;
				long t = l.o + r.o;

				if (t > max)
					max = t;
			} else {
				i++;
				j--;

				long t = l.o + r.o;
				if (t > max)
					max = t;
			}

		}

		out.print(max);
		in.close();
		out.flush();
		out.close();

	}

	public static class Pair {
		public long n, o;

		@Override
		public String toString() {
			return "Pair [n=" + n + ", o=" + o + "]";
		}

		public Pair(long n, long o) {
			super();
			this.n = n;
			this.o = o;
		}
	}

}
