package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class BerryPicking {
	static int n, k;
	static int[] arr;

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("berries.in"));
			out = new PrintWriter("berries.out");
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		n = in.nextInt();
		k = in.nextInt();
		arr = new int[n];

		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}

		Arrays.sort(arr);

		int max = Integer.MIN_VALUE;
		for (int i = 1; i <= 1000; i++) {
			if (getCount(i) > max) {
				max = getCount(i);
			}
		}

		out.print(max);
		in.close();
		out.flush();
		out.close();
	}

	static int getCount(int maxBucketSize) {
		int[] arr = BerryPicking.arr.clone();

		int[] buckets = new int[k];
		int j = 0;
		for (int i = n - 1; i >= 0; i--) {
			while (arr[i] >= maxBucketSize && j < buckets.length) {
				buckets[j] = maxBucketSize;
				j++;
				arr[i] -= maxBucketSize;
			}
		}
		Arrays.sort(arr);
		for (int i = n - 1; i >= 0; i--) {
			if (j >= buckets.length)
				break;

			buckets[j] = arr[i];
			j++;
		}

		Arrays.sort(buckets);
		int sum = 0;

		for (int i = 0; i <= k / 2 - 1; i++) {
			sum += buckets[i];
		}

		return sum;
	}

}
