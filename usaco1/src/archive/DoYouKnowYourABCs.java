package archive;
import java.util.Scanner;

public class DoYouKnowYourABCs {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		in.close();
	}

	static long[] getSolutions(long[][] inputMat) {
		Rational[][] mat = new Rational[3][4];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				mat[i][j] = new Rational(inputMat[i][j]);
			}
		}
	}

	static class Rational {
		final long n, d;

		public Rational(long n) {
			this.n = n;
			d = 1;
		}

		public Rational(long n, long d) {
			this.n = n;
			this.d = d;
		}

		public Rational multiply(Rational o) {
			return new Rational(n * o.n, d * o.d);
		}

		public Rational add(Rational o) {
			return new Rational(n * o.d + o.n * d, d * o.d);
		}

		public Rational subtract(Rational o) {
			return new Rational(n * o.d - o.n * d, d * o.d);
		}

		public Rational divide(Rational o) {
			return new Rational(n * o.d, d * o.n);
		}

		boolean isInteger() {
			return n % d == 0;
		}

		long longValue() {
			return n / d;
		}
	}
}
