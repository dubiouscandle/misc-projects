package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SocialDistancing {

	public static void main(String[] args) throws FileNotFoundException {
//		Scanner in = new Scanner(System.in);
		Scanner in = new Scanner(new File("socdist.in"));
		PrintWriter out = new PrintWriter(new File("socdist.out"));

		int n = in.nextInt();
		int m = in.nextInt();

		long[] arrA = new long[m];
		long[] arrB = new long[m];

		for (int i = 0; i < m; i++) {
			arrA[i] = in.nextLong();
			arrB[i] = in.nextLong();
		}

		Arrays.sort(arrA);
		Arrays.sort(arrB);

		long max = 1_000_000_000_000_000_000L;
		long min = 0;

		while (min < max) {
			long mid = (min + max + 1) / 2;

			boolean works = test(arrA, arrB, mid, n);

			if (works) {
				min = mid;
			} else {
				max = mid - 1;
			}
		}

		out.println(min);
		out.flush();
	}

	public static boolean test(long[] arrA, long[] arrB, long d, int n) {
		int m = arrA.length;

		long cow = arrA[0];
		n--;

		for (int i = 0; i < m; i++) {
			long l = arrA[i];
			long r = arrB[i];

			long next = Math.max(l, cow + d);

			while (next <= r) {
				cow = next;
				next = Math.max(l, cow + d);
				n--;
			}

			if (n <= 0)
				return true;

		}

		return false;

	}
}
