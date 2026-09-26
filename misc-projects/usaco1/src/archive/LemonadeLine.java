package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class LemonadeLine {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("lemonade.in"));
		PrintWriter out = new PrintWriter(new File("lemonade.out"));
//		Scanner in = new Scanner(System.in);
//		PrintWriter out = new PrintWriter(System.out);

		int n = in.nextInt();
		long[] arr = new long[n];
		for (int i = 0; i < n; i++) {
			arr[i] = in.nextLong();
		}

		Arrays.sort(arr);

		for (int i = 0, j = n - 1; i < j; i++, j--) {
			long temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}

		for (int i = 0; i < n; i++) {
			if (i <= arr[i])
				continue;

			out.println(i);
			out.flush();
			return;
		}

		out.print(n);
		out.flush();

	}

}
