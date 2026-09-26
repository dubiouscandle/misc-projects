package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoanRepayment {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("loan.in"));
			out = new PrintWriter(new File("loan.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		long n = in.nextLong();
		long k = in.nextLong();
		long m = in.nextLong();

		int max = 1_000_000_000;
		int min = 0;

		while (min < max) {
			int mid = (min + max + 1) / 2;

			boolean works = works(n, k, m, mid);

			if (works) {
				min = mid;
			} else {
				max = mid - 1;
			}
		}

		out.print(min);
		out.flush();
	}

	private static boolean works(long n, long k, long m, int x) {
		long y;
		do {
			y = n / x;

			n -= y;
			k--;
		} while (y > m);

		k -= (n + m - 1) / m;

		return k >= 0;
	}

}
