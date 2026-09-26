package bronze_february2023_solved;

import java.util.Scanner;

public class HungryCow {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numDeliveries = s.nextInt();
		long numDays = s.nextLong();

		long[] numHayBales = new long[numDeliveries];
		long[] days = new long[numDeliveries];

		for (int i = 0; i < numDeliveries; i++) {
			days[i] = s.nextLong();
			numHayBales[i] = s.nextLong();
		}

		s.close();

		long dayWhenRunOut = 1;
		long numEmptyDays = 0;

		for (int i = 0; i < numDeliveries; i++) {
			long day = days[i];

			if (day >= dayWhenRunOut) {
				numEmptyDays += day - dayWhenRunOut;
				dayWhenRunOut = day + numHayBales[i];
			} else {
				dayWhenRunOut += numHayBales[i];
			}
		}

		if (numDays >= dayWhenRunOut) {
			numEmptyDays += numDays - dayWhenRunOut + 1;
		}

		System.out.println(numDays - numEmptyDays);
	}

}
