package archive;
import java.util.ArrayList;
import java.util.Scanner;

public class RangeReconstruction {
	static int n;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		n = in.nextInt();

		long[][] ranges = new long[n][n];

		long[] consecutiveDiffs = new long[n - 1];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				ranges[i][j] = in.nextLong();
			}
		}

		for (int i = 0; i < n - 1; i++) {
			consecutiveDiffs[i] = ranges[i][i + 1];
		}

		ArrayList<Long> arr = new ArrayList<>();
		arr.add(0L);

		for (int i = 0; i < n - 1; i++) {
			long prev = arr.get(i);

			arr.add(prev + consecutiveDiffs[i]);

			if (works(arr, ranges)) {
				continue;
			}

			arr.remove(arr.size() - 1);
			arr.add(prev - consecutiveDiffs[i]);
		}

		for (int i = 0; i < arr.size(); i++) {
			System.out.print(arr.get(i));
			if (i != arr.size() - 1) {
				System.out.print(" ");
			}
		}
	}

	static boolean works(ArrayList<Long> arr, long[][] ranges) {
		int len = arr.size();

		for (int i = 0; i < len; i++) {
			long min = arr.get(i);
			long max = arr.get(i);
			for (int j = i; j < len; j++) {
				min = Math.min(min, arr.get(j));
				max = Math.max(max, arr.get(j));

				if (max - min != ranges[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

}
