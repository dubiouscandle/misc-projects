package archive;
import java.util.Arrays;
import java.util.Scanner;

public class MilkSum {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}

		int q = in.nextInt();
		int[] qIndices = new int[q];
		int[] qjs = new int[q];
		for (int i = 0; i < q; i++) {
			qIndices[i] = arr[in.nextInt() - 1];
			qjs[i] = in.nextInt();
		}

		Arrays.sort(arr);
		long sum = 0;
		long t = 0;
		long[] prefixSum = new long[n];
		for (int i = 0; i < n; i++) {
			sum += arr[i];
			prefixSum[i] = sum;
			t += (long) arr[i] * (i + 1);
		}

		for (int i = 0; i < q; i++) {
			qIndices[i] = Arrays.binarySearch(arr, qIndices[i]);
		}

//		System.out.println(Arrays.toString(arr));
//		System.out.println(Arrays.toString(prefixSum));

		for (int i = 0; i < q; i++) {
			int index = qIndices[i];
			int val = arr[index];
			int nVal = qjs[i];

			int nIndex = binarySearchIgnore(arr, nVal, index);

			if (nIndex < 0)
				nIndex = -nIndex - 1;

//			System.out.println(index + " " + nIndex);

			if (index == nIndex) {
				System.out.println(t - (long) (index + 1) * (val - nVal));
			} else if (nIndex > index) {
				long shiftDiff = prefixSum[nIndex] - prefixSum[index];
				long valDiff = -(long) (index + 1) * val + (long) (nIndex + 1) * nVal;
				System.out.println(t - shiftDiff + valDiff);
			} else {

				long shiftDiff = prefixSum[index - 1] - ((nIndex == 0) ? 0 : prefixSum[nIndex - 1]);
				long valDiff = -(long) (index + 1) * val + (long) (nIndex + 1) * nVal;
				System.out.println(t + shiftDiff + valDiff);
			}
		}
	}

	private static int binarySearchIgnore(int[] arr, int val, int skip) {
		int index = Arrays.binarySearch(arr, val);

		if (index < 0) {
			index = -index - 1;
		}

		if (index <= skip) {
			return index;
		} else {
			return index - 1;
		}

	}

	/*
	 * 10 1 2 19 26 31 37 38 42 44 46 1 10 36
	 */

	/*
	 * 332625637528119915 332620356050191146
	 */
}
