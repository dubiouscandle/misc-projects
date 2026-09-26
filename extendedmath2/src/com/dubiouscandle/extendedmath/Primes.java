package com.dubiouscandle.extendedmath;

public class Primes {
	public final int count;

	public Primes(int max) {
		count = init(max);
	}

	/**
	 * @param n
	 * @return the nth prime number, zero indexed.
	 */
	public int get(int n) {
		if (n < 0 || n >= count) {
			throw new IllegalArgumentException(n + " out of bounds for length " + count);
		}

		return primes[n];
	}

	/**
	 * @param n
	 * @return if n is prime
	 */
	public boolean isPrime(int n) {
		if (n < 0 || n >= count) {
			throw new IllegalArgumentException(n + " out of bounds for length " + count);
		}

		return isPrime[n];
	}

	private int[] primes;
	private boolean[] isPrime;

	private int init(int max) {
		isPrime = new boolean[max + 1];

		for (int i = 2; i <= max; i++) {
			isPrime[i] = true;
		}

		for (int i = 2; i * i <= max; i++) {
			if (isPrime[i]) {
				for (int j = i * i; j <= max; j += i) {
					isPrime[j] = false;
				}
			}
		}

		int count = 0;
		for (int i = 2; i <= max; i++) {
			if (isPrime[i]) {
				count++;
			}
		}

		primes = new int[count];
		int j = 0;
		for (int i = 2; i <= max; i++) {
			if (isPrime[i]) {
				primes[j] = i;
				j++;
			}
		}

		return count;
	}
}
