package archive;
import java.util.Scanner;

import util.IntegerCondition;

public class Bakery {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = in.nextInt(); i >= 1; i--) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		int n = in.nextInt();
		long tA = in.nextLong();
		long tB = in.nextLong();

		long[] arrA = new long[n];
		long[] arrB = new long[n];
		long[] arrC = new long[n];

		for (int i = 0; i < n; i++) {
			arrA[i] = in.nextLong();
			arrB[i] = in.nextLong();
			arrC[i] = in.nextLong();
		}

		long max = tA - tB - 2;
		long min = 0;

		while (min < max) {
			long mid = (min + max) / 2;

			boolean works = works(mid, tA, tB, arrA, arrB, arrC);

			if (works) {
				max = mid;
			} else {
				min = mid + 1;
			}
		}

		System.out.println(min);

	}

	private static boolean works(long k, long tA, long tB, long[] arrA, long[] arrB, long[] arrC) {
		for (int i = 0; i < tA - tB - 2; i++) {

		}
		return false;
	}

}
