package team29;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Team29Problem8 {

	public static void main(String[] args) {
		// note: this was programmed after the competition had concluded
		// also, this is extremely inefficient with all the nested loops. I am sure
		// there is a more clever solution, however this is the solution I first thought
		// of.
		Scanner s = new Scanner(System.in);

		int numSeats = s.nextInt();
		int numAisles = s.nextInt();

		s.close();

		int base = numSeats - 1;
		int totalNonUniqueCombinations = 1;

		for (int i = 0; i < numAisles; i++) {
			totalNonUniqueCombinations *= base;
		}

		// generate all combinations of aisle placements
		List<int[]> database = new ArrayList<>();

		outer: for (int n = 0; n < totalNonUniqueCombinations; n++) {
			// for each position id number n, decode the aisle positions
			int[] aislePositions = new int[numAisles];

			boolean[] aisleExists = new boolean[base];

			int nCopy = n;

			for (int i = 0; i < numAisles; i++) {
				int position = nCopy % base;

				if (aisleExists[position])
					continue outer;

				aisleExists[position] = true;
				aislePositions[i] = nCopy % base;
				nCopy /= base;
			}

			if (!findMatchingArray(database, aislePositions, base))
				database.add(aislePositions);
		}

		// now just compare to find the minimum inconvenience
		int minInconvenience = Integer.MAX_VALUE;
		int numSuchConfigs = 0;
		for (int[] aislePositions : database) {
			int inconvenience = 0;

			for (int seatNum = 0; seatNum < numSeats; seatNum++) {
				// find distance to closest aisle
				int minDistance = Integer.MAX_VALUE;

				for (int i = 0; i < aislePositions.length; i++) {
					int distance;
					if (seatNum <= aislePositions[i]) {
						distance = aislePositions[i] - seatNum;
					} else {
						distance = seatNum - aislePositions[i] - 1;
					}

					if (distance < minDistance)
						minDistance = distance;
				}

				inconvenience += minDistance;
			}

			if (inconvenience < minInconvenience) {
				minInconvenience = inconvenience;
				numSuchConfigs = 1;
			} else if (inconvenience == minInconvenience) {
				numSuchConfigs++;
			}

		}
		
		System.out.println(minInconvenience);
		System.out.println(numSuchConfigs);
	}

	public static boolean findMatchingArray(List<int[]> database, int[] arrayToMatch, int maxValue) {
		if (database.size() == 0)
			return false;

		for (int[] element : database) {
			// for each element find if it matches arrayToMatch
			boolean[] found1 = new boolean[maxValue];
			boolean[] found2 = new boolean[maxValue];

			for (int i = 0; i < element.length; i++) {
				found1[element[i]] = true;
				found2[arrayToMatch[i]] = true;
			}

			boolean allMatch = true;

			for (int i = 0; i < maxValue; i++) {
				if (found1[i] != found2[i])
					allMatch = false;
			}

			if (allMatch)
				return true;

		}

		return false;
	}

}
