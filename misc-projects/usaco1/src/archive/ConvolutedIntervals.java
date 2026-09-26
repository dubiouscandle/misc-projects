package archive;

import java.util.Scanner;

public class ConvolutedIntervals {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int m = in.nextInt();

		int[] countA = new int[m + 1];
		int[] countB = new int[m + 1];

		for (int i = 0; i < n; i++) {
			countA[in.nextInt()]++;
			countB[in.nextInt()]++;
		}

		in.close();

		long[] sumsA = new long[2 * m + 1];
		long[] sumsB = new long[2 * m + 1];

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= m; j++) {
				sumsA[i + j] += (long) countA[i] * countA[j];
				sumsB[i + j] += (long) countB[i] * countB[j];
			}
		}

		StringBuilder out = new StringBuilder();
		long x = 0;
		for (int i = 0; i < 2 * m + 1; i++) {
			x += sumsA[i];
			out.append(x).append('\n');
			x -= sumsB[i];
		}

		System.out.print(out);
	}

}
