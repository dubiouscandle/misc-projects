package archive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OutOfSorts {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("sort.in"));
		PrintWriter out = new PrintWriter(new File("sort.out"));

		int n = in.nextInt();
		long[] arr = new long[n];

		for (int i = 0; i < n; i++) {
			arr[i] = in.nextLong();
		}

		long c = 0;
		boolean sorted = false;

		while (!sorted) {
			sorted = true;
			c++;
			for (int i = 0; i <= n - 2; i++) {
				if (arr[i + 1] < arr[i]) {
					long temp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = temp;
					sorted = false;
				}
			}
		}
		
		out.print(c);
		out.flush();
	}

}
