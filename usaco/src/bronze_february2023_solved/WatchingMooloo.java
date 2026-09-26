package bronze_february2023_solved;

import java.util.Scanner;

public class WatchingMooloo {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numDays = s.nextInt();
		long numToStart = s.nextLong() + 1;

		long[] days = new long[numDays];

		for (int i = 0; i < numDays; i++) {
			days[i] = s.nextLong();
		}

		s.close();

		if (numDays == 0) {
			System.out.println(0);
			return;
		}

		long sum = numToStart;

		for (int i = 1; i < numDays; i++) {
			long day = days[i];
			long prevDay = days[i - 1];

			long diff = day - prevDay;

			if (diff <= numToStart) {
				sum += diff;
			} else {
				sum += numToStart;
			}

		}

		System.out.println(sum);
	}

}
