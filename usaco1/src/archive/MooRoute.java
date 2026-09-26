package archive;
import java.util.Scanner;

public class MooRoute {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		int[] arr = new int[n + 2];
		arr[0] = 1;

		for (int i = 0; i < n; i++) {
			arr[i + 1] = in.nextInt();
		}

		StringBuilder out = new StringBuilder();
		int x = 1;
		while (x > 0) {
			while (arr[x] > 0) {
				out.append('R');
				arr[x]--;
				x++;
			}

			while (x != 0 && (arr[x - 1] != 1 || arr[x] <= 0)) {
				out.append('L');
				x--;
				arr[x]--;
			}
		}

		out.setLength(out.length() - 1);

		System.out.println(out);
	}

	/*
	 * 8 2 4 6 8 2 2 4 4
	 */

}
