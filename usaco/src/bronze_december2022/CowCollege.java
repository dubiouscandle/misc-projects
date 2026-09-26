import java.util.Arrays;
import java.util.Scanner;

public class CowCollege {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numCows = s.nextInt();

		long[] costs = new long[numCows];

		for (int i = 0; i < numCows; i++) {
			costs[i] = s.nextInt();
		}

		s.close();

		Arrays.sort(costs);

		long maxMoney = Long.MIN_VALUE;
		long optimalCharge = Long.MIN_VALUE;

		for (int i = 0; i < numCows; i++) {
			long money = (numCows - i) * costs[i];

			if (money > maxMoney) {
				optimalCharge = costs[i];
				maxMoney = money;
			}
		}

		System.out.print(maxMoney + " " + optimalCharge);
	}
}
