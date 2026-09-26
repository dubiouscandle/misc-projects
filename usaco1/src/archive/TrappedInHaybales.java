package archive;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class TrappedInHaybales {

	public static void main(String[] args) {
		Scanner in;
		PrintWriter out;

		try {
			in = new Scanner(new File("trapped.in"));
			out = new PrintWriter(new File("trapped.out"));
		} catch (Exception e) {
			in = new Scanner(System.in);
			out = new PrintWriter(System.out);
		}

		long n = in.nextLong();
		long b = in.nextLong();

		long[] arr = new int[n];

		in.close();
		out.flush();
		out.close();
	}

}
