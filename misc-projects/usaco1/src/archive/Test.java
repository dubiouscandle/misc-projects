package archive;
import java.util.Arrays;
import java.util.Scanner;

public class Test {

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
			qIndices[i] = in.nextInt() - 1;
			qjs[i] = in.nextInt();
		}

		for (int i = 0; i < q; i++) {
			int[] nArr = arr.clone();

			nArr[qIndices[i]] = qjs[i];
			Arrays.sort(nArr);

			long sum = 0;
			for (int j = 0; j < nArr.length; j++) {
				sum += (long) nArr[j] * (j + 1);
			}

			System.out.println(sum);
		}
	}
	
	/*
	10
	1 2 3 4 5 10 10 17 19 3
	1
	3 5
	 * */
}
