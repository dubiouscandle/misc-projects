
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CircularBarn {
	private static final int MAX_NUM_COWS = 1000;
//	private static final int MAX_NUM_COWS = 500;

	private static int[] winners = new int[MAX_NUM_COWS + 1];
	private static int[] turns = new int[MAX_NUM_COWS + 1];
	private static BitSet isValidCows = new BitSet(MAX_NUM_COWS + 1);

	private static int[] validCows;

	static {
		isValidCows.set(1, MAX_NUM_COWS + 1);

		for (int i = 2; i * i <= MAX_NUM_COWS; i++) {
			if (!isValidCows.get(i))
				continue;

			for (int j = i * 2; j <= MAX_NUM_COWS; j += i) {
				isValidCows.set(j, false);
			}
		}

		int numValidCows = isValidCows.cardinality();

		validCows = new int[numValidCows];

		int j = 0;
		for (int i = 0; i <= MAX_NUM_COWS; i++) {
			if (isValidCows.get(i)) {
				validCows[j] = i;
				j++;
			}
		}

		for (int numCows = 1; numCows <= MAX_NUM_COWS; numCows++) {
			if (isValidCows.get(numCows)) {
				winners[numCows] = 1;
				turns[numCows] = 1;
				continue;
			}

			boolean canGetTo2 = false;

			int pointer1 = 0;
			while (pointer1 < validCows.length && numCows - validCows[pointer1] > 0) {
				if (winners[numCows - validCows[pointer1]] == 2)
					canGetTo2 = true;

				pointer1++;
			}

			if (canGetTo2) {

				winners[numCows] = 1;

				List<Integer> indicesWith2 = new LinkedList<>();
				int pointer2 = 0;

				while (pointer2 < validCows.length && numCows - validCows[pointer2] > 0) {
					if (winners[numCows - validCows[pointer2]] == 2)
						indicesWith2.add(numCows - validCows[pointer2]);

					pointer2++;
				}

				Iterator<Integer> iterator = indicesWith2.iterator();

				int minTurns = Integer.MAX_VALUE;
				while (iterator.hasNext()) {
					int index = iterator.next();

					if (turns[index] < minTurns)
						minTurns = turns[index];

				}

				turns[numCows] = minTurns + 1;

			} else {
				winners[numCows] = 2;

				List<Integer> indicesWith1 = new LinkedList<>();
				int pointer2 = 0;

				while (pointer2 < validCows.length && numCows - validCows[pointer2] > 0) {
					if (winners[numCows - validCows[pointer2]] == 1)
						indicesWith1.add(numCows - validCows[pointer2]);

					pointer2++;
				}

				Iterator<Integer> iterator = indicesWith1.iterator();

				int maxTurns = Integer.MIN_VALUE;
				while (iterator.hasNext()) {
					int index = iterator.next();

					if (turns[index] > maxTurns)
						maxTurns = turns[index];

				}

				turns[numCows] = maxTurns + 1;

			}

		}

	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numTestCases = s.nextInt();

		for (int i = 0; i < numTestCases; i++) {
			handle(s);
		}

		s.close();
	}

	private static void handle(Scanner s) {
		int numCows = s.nextInt();

		if (numCows == 1) {
			int onlyCow = s.nextInt();
			if (winners[onlyCow] == 1) {
				System.out.println("Farmer John");
			} else {
				System.out.println("Farmer Nhoj");
			}

			return;
		}

		int[] cows = new int[numCows];
		int[] numTurns = new int[numCows];

		for (int i = 0; i < numCows; i++) {
			cows[i] = s.nextInt();
			numTurns[i] = turns[cows[i]];
		}

		for (int i = 1;; i += 2) {
			int numToFind1 = i;
			int numToFind2 = i + 1;

			for (int j = 0; j < numTurns.length; j++) {
				if (numTurns[j] == numToFind1) {
					System.out.println("Farmer John");
					return;
				} else if (numTurns[j] == numToFind2) {
					System.out.println("Farmer Nhoj");
					return;
				}

			}
		}
	}
}
