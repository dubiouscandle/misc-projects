package team29;

import java.util.Scanner;

public class Team29Problem9 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int d = s.nextInt();
		int n = s.nextInt();

		int numMoves = 0;
		String moves = "";
		
		boolean[][] possiblePasswords = new boolean[n][];

		for (int i = 0; i < n; i++) {
			String password = s.next();
			possiblePasswords[i] = new boolean[d];

			for (int j = 0; j < password.length(); j++) {
				possiblePasswords[i][j] = (password.charAt(j) == '1') ? true : false;

			}
		}

		s.close();

		boolean[] found = new boolean[n];

		boolean[] currentPosition = new boolean[d];

		int foundIndex = -1000;
		boolean previousMoveWasReset = true;

		while (true) {
			int button = -100;

			for (int i = 0; i < currentPosition.length; i++) {
				if (currentPosition[i] == false) {
					currentPosition[i] = true;
				} else {
					continue;
				}

				// searches for matches after the change
				for (int j = 0; j < possiblePasswords.length; j++) {
					if (found[j])
						continue;

					boolean matches = true;

					// finds it equals
					for (int k = 0; k < currentPosition.length; k++) {
						if (currentPosition[k] != possiblePasswords[j][k])
							matches = false;
					}

					if (matches) {
						button = i;
						foundIndex = j;
						break;
					}

				}

				currentPosition[i] = false;
			}

			if (foundIndex != -1000)
				found[foundIndex] = true;

			if (button == -100) {
				if (previousMoveWasReset) {
					previousMoveWasReset = false;

					int indexWithLeastClicks = -100;
					int leastClicks = 1000;

					for (int i = 0; i < possiblePasswords.length; i++) {
						if (found[i])
							continue;

						int counter = 0;

						for (int j = 0; j < possiblePasswords[i].length; j++) {
							if (possiblePasswords[i][j])
								counter++;
						}

						if (counter < leastClicks) {
							leastClicks = counter;
							indexWithLeastClicks = i;
						}
					}

					boolean[] password = possiblePasswords[indexWithLeastClicks];
					found[indexWithLeastClicks] = true;

					for (int i = 0; i < password.length; i++) {
						if (password[i]) {
							moves += i + " ";
							numMoves++;
							currentPosition[i] = true;
						}
					}
				} else {
					moves += "r ";
					numMoves++;
					currentPosition = new boolean[d];
					previousMoveWasReset = true;
				}
			} else {
				currentPosition[button] = true;
				moves += button + " ";
				numMoves++;
				previousMoveWasReset = false;
			}

			boolean allAreFound = true;

			for (int i = 0; i < found.length; i++) {
				if (found[i] == false) {
					allAreFound = false;
				}

			}

			if (allAreFound)
				break;
		}
		
		System.out.println(numMoves);
		System.out.println(moves);
	}
}
