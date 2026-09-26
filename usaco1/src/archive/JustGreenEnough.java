package archive;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class JustGreenEnough {
	static int n;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		n = in.nextInt();

		int[][] grid = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				grid[i][j] = in.nextInt();
			}
		}

		int[][] min100Grid = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				min100Grid[i][j] = (grid[i][j] >= 100) ? 1 : 0;
			}
		}
		int[][] min101Grid = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				min101Grid[i][j] = (grid[i][j] >= 101) ? 1 : 0;
			}
		}

		long sum = countRects(min100Grid) - countRects(min101Grid);
		System.out.println(sum);
	}

	private static long countRects(int[][] min100Grid) {
		long sum = 0;

		int[] histogram = new int[n];

		for (int[] row : min100Grid) {
			for (int i = 0; i < n; i++) {
				if (row[i] == 1)
					histogram[i]++;
				else
					histogram[i] = 0;
			}

			sum += countRects(histogram);
		}

		return sum;
	}

	private static long countRects(int[] histogram) {
		long sum = 0;
		long c = 0;
		Stack<Long> stack = new Stack<>();
		stack.push(0L);
		for (int i = 0; i < histogram.length; i++) {
			long h = histogram[i];
			if (stack.peek() <= h) {
				stack.push(h);
				sum += h;
			} else {
				long diff = 0;

				int p = 0;
				while (stack.peek() > h) {
					p++;
					diff += h - stack.pop();
				}
				for(int j = 0; j < p; j++) {
					stack.push(h);
				}
				sum += diff;
				stack.push(h);
				sum += h;
			}

			c += sum;
		}

		return c;
	}

}
