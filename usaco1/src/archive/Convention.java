package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Convention {
	static int n, m, c;

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("convention.in"));
			out = new PrintWriter(new File("convention.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		n = in.nextInt();
		m = in.nextInt();
		c = in.nextInt();

		long[] arr = new long[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextLong();
		}

		Arrays.sort(arr);

		int max = 1_000_000_000;
		int min = 0;

		while (min < max) {
			int mid = (min + max) / 2;

			boolean works = works(arr, mid);

			if (works) {
				max = mid;
			} else {
				min = mid + 1;
			}
		}

		out.print(min);
		in.close();
		out.flush();
		out.close();

	}

	private static boolean works(long[] arr, int t) {
		Stack<Long> stack = new Stack<>();
		int b = 1;
		for (int i = 0; i < n; i++) {
			long cur = arr[i];

			if (stack.isEmpty()) {
				stack.add(cur);
				continue;
			}

			long maxT = stack.get(0).longValue() + t;
			if (cur > maxT || stack.size() >= c) {
				stack = new Stack<Long>();
				stack.add(cur);
				b++;
			} else {
				stack.add(cur);
			}
		}

		return b <= m;
	}

}
