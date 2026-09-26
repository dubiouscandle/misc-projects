package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class MooBuzz {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("moobuzz.in"));
			out = new PrintWriter(new File("moobuzz.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		long n = in.nextLong();

		for (long i = 1;; i++) {
			if (c(i) == n) {
				out.print(i);
				break;
			}
		}

		in.close();
		out.flush();
		out.close();

	}

	public static long c(long x) {
		return x - x / 3 - x / 5 + x / 15;
	}
}
