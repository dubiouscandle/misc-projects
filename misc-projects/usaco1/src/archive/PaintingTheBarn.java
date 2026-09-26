package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PaintingTheBarn {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("paintbarn.in"));
		PrintWriter out = new PrintWriter(new File("paintbarn.out"));

		int n = in.nextInt();
		int k = in.nextInt();

		int[][] grid = new int[1001][1001];

		for (int i = 0; i < n; i++) {
			int x1 = in.nextInt();
			int y1 = in.nextInt();
			int x2 = in.nextInt();
			int y2 = in.nextInt();

			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					grid[x][y]++;
				}
			}
		}

		in.close();

		int c = 0;
		for (int x = 0; x <= 1000; x++) {
			for (int y = 0; y <= 1000; y++) {
				if (grid[x][y] == k)
					c++;
			}
		}

		out.println(c);
		out.flush();
		out.close();
	}

}
