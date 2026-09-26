package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class YearOfTheCow {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int k = in.nextInt();

		long[] arr = new long[n];
		for (int i = 0; i < n; i++)
			arr[i] = in.nextLong();

		in.close();

		Arrays.sort(arr);
		ArrayList<Integer> intervals = new ArrayList<>();
		intervals.add(-1);
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			int interval = (int) (arr[i] / 12);
			if (intervals.size() != 0 && intervals.get(intervals.size() - 1) == interval)
				continue;

			if (interval > max)
				max = interval;

			intervals.add(interval);
		}

		ArrayList<Integer> gaps = new ArrayList<>();
		for (int i = 0; i < intervals.size() - 1; i++) {
			gaps.add(intervals.get(i + 1) - intervals.get(i) - 1);
		}

		Collections.sort(gaps);

		long sum = 0;
		for (int i = gaps.size() - 1; i > gaps.size() - k; i--) {
			sum += gaps.get(i);
		}

		System.out.println(12 * (max + 1 - sum));

	}

}
