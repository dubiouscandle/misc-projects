import java.math.BigInteger;

public class HungryHungryDinosaurs {
	public static void main(String[] args) {
		int c = 40;
		int o = 50;
		int h = 60;
		solution(c, o, h);
	}

	public static void solution(int c, int o, int h) {
		BigInteger sum = BigInteger.ZERO;

		int boundI = Math.min(c, o + 1);
		for (int i = 1; i <= boundI; i++) {
			int boundJ = Math.min(h, o + 1 - i);
			for (int j = 1; j <= boundJ; j++) {
				BigInteger term = BigInteger.ONE;
				term = term.multiply(nCr(o + 1, i));
				term = term.multiply(nCr(o + 1 - i, j));
				term = term.multiply(nCr(c - 1, i - 1));
				term = term.multiply(nCr(h - 1, j - 1));

				sum = sum.add(term);
			}
		}

		System.out.println(sum);
		System.out.println(sum.mod(BigInteger.valueOf(1_000_000_007L)));
	}

	public static BigInteger nCr(int n, int r) {
		if (r > n || r < 0 || n < 0) {
			return BigInteger.ZERO;
		}

		BigInteger numer = BigInteger.ONE;

		for (int i = n; i > (n - r); i--) {
			numer = numer.multiply(BigInteger.valueOf(i));
		}

		BigInteger denom = BigInteger.ONE;
		for (int i = 1; i <= r; i++) {
			denom = denom.multiply(BigInteger.valueOf(i));
		}

		return numer.divide(denom);
	}
}
