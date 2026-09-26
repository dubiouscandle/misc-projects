package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DiamondCollector {

	public static void main(String[] args) throws FileNotFoundException {
//		Scanner in = new Scanner(System.in);
//		PrintWriter out = new PrintWriter(System.out);
		Scanner in = new Scanner(new File("diamond.in"));
		PrintWriter out = new PrintWriter(new File("diamond.out"));

		int n = in.nextInt();
		int k = in.nextInt();

		long[] arr = new long[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextLong();
		}

		Arrays.sort(arr);

		int[] maxArr = new int[n];

		Queue<Long> queue = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			queue.add(arr[i]);

			while (queue.peek() + k < arr[i]) {
				queue.poll();
			}

			maxArr[i] = queue.size();
		}

		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (arr[i] + k >= arr[j]) {
					continue;
				}

				int c = maxArr[i] + maxArr[j];
				if(c > max)
					max = c;
			}
		}

		out.print(max);
		out.flush();
		
		in.close();
		out.close();
	}

}
