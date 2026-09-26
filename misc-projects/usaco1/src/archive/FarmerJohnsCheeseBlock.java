package archive;
import java.util.Scanner;

public class FarmerJohnsCheeseBlock {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int q = in.nextInt();

		int[][] xy = new int[n][n];
		int[][] yz = new int[n][n];
		int[][] zx = new int[n][n];

		int c = 0;

		StringBuilder out = new StringBuilder(q * 4);
		for (int i = 0; i < q; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			int z = in.nextInt();

			xy[x][y]++;
			yz[y][z]++;
			zx[z][x]++;

			if (xy[x][y] == n)
				c++;
			if (yz[y][z] == n)
				c++;
			if (zx[z][x] == n)
				c++;

			out.append(c).append('\n');
		}

		System.out.print(out);

		in.close();

	}
}
