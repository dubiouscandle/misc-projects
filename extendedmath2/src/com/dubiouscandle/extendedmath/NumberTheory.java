package com.dubiouscandle.extendedmath;

public class NumberTheory {
	/**
	 * @param a
	 * @param b
	 * @return the greatest common divisor of a and b
	 */
	public static int gcd(int a, int b) {
		while (b != 0) {
			a %= b;
			int temp = a;
			a = b;
			b = temp;
		}

		return a;
	}
	/**
	 * @param a
	 * @param b
	 * @return the greatest common divisor of a and b
	 */
	public static long gcd(long a, long b) {
		while (b != 0) {
			a %= b;
			long temp = a;
			a = b;
			b = temp;
		}

		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @return a ^ b. If b is negative, the method returns 1.
	 */
	public static int pow(int a, int b) {
		int product = 1;

		while (b > 0) {
			if ((b & 1) == 1) {
				product *= a;
			}
			a = a * a;
			b >>= 1;
		}

		return product;
	}

	/**
	 * @param a
	 * @param b
	 * @return a ^ b. If b is negative, the method returns 1.
	 */
	public static long pow(long a, long b) {
		long product = 1;

		while (b > 0) {
			if ((b & 1) == 1) {
				product *= a;
			}
			a = a * a;
			b >>= 1;
		}

		return product;
	}

	/**
	 * @param n
	 * @return the factorial of n
	 */
	public static long factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be greater than or equal to 0.");
		}

		long product = 1;

		for (int i = 2; i <= n; i++) {
			product *= i;
		}

		return product;
	}

	/**
	 * @param a
	 * @param b
	 * @param mod
	 * @return (a ^ b) % mod
	 */
	public static int modExp(int a, int b, int mod) {
		int result = 1;
		a = a % mod;
		while (b > 0) {
			if ((b % 2) == 1) {
				result = (result * a) % mod;
			}
			b = b >> 1; // Divide the exponent by 2
			a = (a * a) % mod;
		}
		return result;
	}

	/**
	 * @param a
	 * @param b
	 * @param mod
	 * @return (a ^ b) % mod
	 */
	public static long modExp(long a, long b, long mod) {
		long result = 1;
		a = a % mod;
		while (b > 0) {
			if ((b % 2) == 1) {
				result = (result * a) % mod;
			}
			b = b >> 1; // Divide the exponent by 2
			a = (a * a) % mod;
		}
		return result;
	}

	/**
	 * @param n
	 * @return If n is prime. If n is less than or equal to 1, false is returned.
	 */
	public static boolean isPrime(int n) {
		if (n == 2 || n == 3) {
			return true;
		}

		if (n <= 1 || n % 2 == 0 || n % 3 == 0) {
			return false;
		}

		for (int i = 5; i * i <= n; i += 6) {
			if (n % i == 0 || n % (i + 2) == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param n
	 * @return If n is prime. If n is less than or equal to 1, false is returned.
	 */
	public static boolean isPrime(long n) {
		if (n == 2 || n == 3) {
			return true;
		}

		if (n <= 1 || n % 2 == 0 || n % 3 == 0) {
			return false;
		}

		for (long i = 5; i * i <= n; i += 6) {
			if (n % i == 0 || n % (i + 2) == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param a
	 * @param b
	 * @return the least common multiple of a and b
	 */
	public static int lcm(int a, int b) {
	    return Math.abs(a * b) / gcd(a, b);
	}

	/**
	 * @param a
	 * @param b
	 * @return the least common multiple of a and b
	 */
	public static long lcm(long a, long b) {
	    return Math.abs(a * b) / gcd(a, b);
	}
}
