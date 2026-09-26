package bronze_february2023_solved;

import java.util.Scanner;

public class StampPaintings {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numTestCases = s.nextInt();

		for (int i = 0; i < numTestCases; i++) {
			handle(s);
		}

	}

	private static void handle(Scanner s) {
		int paintingSize = s.nextInt();
		boolean painting[][] = new boolean[paintingSize][paintingSize];

		{
			String painting1d = "";
			for (int i = 0; i < paintingSize; i++) {
				painting1d += s.next();
			}

			int index = 0;
			for (int y = 0; y < paintingSize; y++) {
				for (int x = 0; x < paintingSize; x++) {
					painting[x][y] = painting1d.charAt(index) == '*';
					index++;
				}
			}
		}

		int stampSize = s.nextInt();
		boolean[][] stamp = new boolean[stampSize][stampSize];

		{
			String stamp1d = "";
			for (int i = 0; i < stampSize; i++) {
				stamp1d += s.next();
			}

			int index = 0;
			for (int y = 0; y < stampSize; y++) {
				for (int x = 0; x < stampSize; x++) {
					stamp[x][y] = stamp1d.charAt(index) == '*';
					index++;
				}
			}
		}
		boolean paintingCopy[][] = new boolean[paintingSize][paintingSize];
		for (int k = 0; k < 4; k++) {
			for (int x = 0; x <= paintingSize - stampSize; x++) {
				for (int y = 0; y <= paintingSize - stampSize; y++) {
					boolean canStamp = true;

					outer: for (int i = 0; i < stampSize; i++) {
						for (int j = 0; j < stampSize; j++) {
							if (stamp[i][j] && !painting[x + i][y + j]) {
								canStamp = false;
								break outer;
							}

						}
					}

					if (canStamp) {
						for (int i = 0; i < stampSize; i++) {
							for (int j = 0; j < stampSize; j++) {
								if (stamp[i][j]) {
									paintingCopy[x + i][y + j] = true;
								}

							}
						}
					}

				}
			}
			rotateMatrix(stamp);

		}

		boolean equals = true;

		for (int y = 0; y < paintingSize; y++) {
			for (int x = 0; x < paintingSize; x++) {
				if (painting[x][y] != paintingCopy[x][y])
					equals = false;
			}
		}

		if (equals)
			System.out.println("YES");
		else
			System.out.println("NO");
	}

	public static void rotateMatrix(boolean[][] matrix) {
		int size = matrix.length;

		for (int i = 0; i < size; i++) {
			for (int j = i; j < size; j++) {
				boolean temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}

		for (int i = 0; i < size / 2; i++) {
			boolean[] temp = matrix[i];
			matrix[i] = matrix[size - 1 - i];
			matrix[size - 1 - i] = temp;
		}
	}

}
