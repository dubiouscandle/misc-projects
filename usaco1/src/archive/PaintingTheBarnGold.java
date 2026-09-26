package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PaintingTheBarnGold {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("paintbarn.in"));
		PrintWriter out = new PrintWriter(new File("paintbarn.out"));

		int n = in.nextInt();
		int k = in.nextInt();

		long[][] mat = new long[201][201];

		for (int i = 0; i < n; i++) {
			int x1 = in.nextInt();
			int y1 = in.nextInt();
			int x2 = in.nextInt();
			int y2 = in.nextInt();

			for (int x = x1; x <= x2; x++) {
				for (int y = y1; y <= y2; y++) {
					mat[x][y]++;
				}
			}
		}

		long[][] prefixSum = new long[201][201];

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				prefixSum[i][j] = mat[i][j] + (i == 0 ? 0 : prefixSum[i - 1][j]) + (j == 0 ? 0 : prefixSum[i][j - 1])
						- (i == 0 || j == 0 ? 0 : prefixSum[i - 1][j - 1]);
			}
		}

		
	}
}
