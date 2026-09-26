package archive;

import java.util.Arrays;
import java.util.Scanner;

public class Acowdemia {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int k = in.nextInt();
		int l = in.nextInt();

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}
		in.close();

		Arrays.sort(arr);

		int min = 0;
		int max = n;

		while (min < max) {
			int mid = (min + max + 1) / 2;
			boolean works = testH(arr, k, l, mid);

			if (works) {
				min = mid;
			} else {
				max = mid - 1;
			}
		}
		
		System.out.println(min);
	}

	public static boolean testH(int[] arr, int k, int l, int h) {
		Arrays.sort(arr);

		int n = arr.length;

		long sum = 0;

		for (int i = n - h; i < n; i++) {
			if (arr[i] + k < h)
				return false;

			sum += Math.min(h, arr[i]);
		}

		if (sum + (long) k * l < (long) h * h)
			return false;

		return true;
	}

	public static int ceilDiv(int a, int b) {
		return a % b == 0 ? a / b : a / b + 1;
	}

}
