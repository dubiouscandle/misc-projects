package archive;
import java.util.Scanner;

public class CakeGame {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int numTestCases = in.nextInt();

		for (int i = 0; i < numTestCases; i++) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		int n = in.nextInt();

		int[] arr = new int[n];

		long t = 0;
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
			t += arr[i];
		}

		long sum = 0;
		int k = n / 2 + 1;
		for (int i = 0; i < k; i++) {
			sum += arr[i];
		}

		long min = sum;
		for (int i = 0; i + k < n; i++) {
			sum -= arr[i];
			sum += arr[i + k];

			if (sum < min)
				min = sum;

		}

		System.out.println(min + " " + (t - min));

	}
}
