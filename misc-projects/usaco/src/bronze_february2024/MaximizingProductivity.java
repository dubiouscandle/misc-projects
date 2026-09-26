
import java.util.Arrays;
import java.util.Scanner;

public class MaximizingProductivity {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numFarms = s.nextInt();
		int numQueries = s.nextInt();

		int[] closingTimes = new int[numFarms];
		int[] timeArrives = new int[numFarms];

		for (int i = 0; i < numFarms; i++) {
			closingTimes[i] = s.nextInt();
		}

		for (int i = 0; i < numFarms; i++) {
			timeArrives[i] = s.nextInt();
		}

		int[] timeWaking = new int[numQueries];
		int[] minFarms = new int[numQueries];

		for (int i = 0; i < numQueries; i++) {
			minFarms[i] = s.nextInt();
			timeWaking[i] = s.nextInt();
		}

		s.close();

		// for each each farm calculate the time you need to wake up to get there
		int[] timeRequiredToWake = new int[numFarms];
		for (int i = 0; i < numFarms; i++) {
			// timeRequiredToWake <= closingTime - timeWaking - 1

			timeRequiredToWake[i] = closingTimes[i] - timeArrives[i] - 1;

		}

		Arrays.sort(timeRequiredToWake);

		for (int i = 0; i < timeRequiredToWake.length / 2; i++) {
			int temp = timeRequiredToWake[i];
			timeRequiredToWake[i] = timeRequiredToWake[timeRequiredToWake.length - i - 1];
			timeRequiredToWake[timeRequiredToWake.length - i - 1] = temp;
		}

		for (int i = 0; i < numQueries; i++) {
			if (timeWaking[i] <= timeRequiredToWake[minFarms[i] - 1]) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

}
