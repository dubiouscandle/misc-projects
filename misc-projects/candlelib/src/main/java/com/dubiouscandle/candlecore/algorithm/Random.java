package com.dubiouscandle.candlecore.algorithm;

public class Random {
	private static final long MULTIPLIER = 6364136223846793005L;
	private static final float FLOAT_NORM = 1.0f / (1 << 24);

	private long state;
	private final long inc;

	/**
	 * creates a new random generator with the specified seed and sequence
	 * 
	 * @param seed the seed to use
	 * @param seq  the sequence to use
	 */
	public Random(long seed, long seq) {
		inc = (seq << 1) | 1;
		state = 0;
		nextInt();
		state += seed;
		nextInt();
	}

	/**
	 * makes a new generator using System.nanotime() as the current seed and an
	 * arbitrary sequence.
	 */
	public Random() {
		this(System.nanoTime(), Long.reverseBytes(System.nanoTime()));
	}

	/**
	 * returns a randomly generated integer value in the range [Integer.MIN_VALUE,
	 * Integer.MAX_VALUE]
	 *
	 * @return a randomly generated int from in the range [Integer.MIN_VALUE,
	 *         Integer.MAX_VALUE]
	 */
	public int nextInt() {
		int xorshifted = (int) (((state >>> 18) ^ state) >>> 27);
		int rot = (int) (state >>> 59);
		state = state * MULTIPLIER + inc;
		return (xorshifted >>> rot) | (xorshifted << -rot);
	}

	/**
	 * returns a randomly generated float in the range [min, max)
	 * 
	 * @param min
	 * @param max
	 * @return a randomly generated float
	 */
	public float nextFloat(float min, float max) {
		return min + nextFloat() * (max - min);
	}

	/**
	 * returns a randomly generated integer value in the range [0, bound)
	 *
	 * @param bound the positive exclusive upper bound
	 * @return a randomly generated int from in the range [0, bound)
	 */
	public int nextInt(int bound) {
		assert bound > 0;

		while (true) {
			long r = (long) nextInt() + (1L << 31);

			if (r < (0x100000000L - (0x100000000L % bound))) {
				return (int) (r % bound);
			}
		}
	}

	/**
	 * returns a randomly generated integer value in the range [min, max)
	 *
	 * @param bound the positive exclusive upper bound
	 * @return a randomly generated int from in the range [min, max)
	 */
	public int nextInt(int min, int max) {
		return min + nextInt(max - min);
	}

	/**
	 * returns a randomly generated boolean.
	 *
	 * @return a randomly generated boolean
	 */
	public boolean nextBoolean() {
		return (nextInt() & 0x80000000) == 0;
	}

	/**
	 * returns a randomly generated float in the range [0, 1)
	 *
	 * @return a randomly generated float
	 */
	public float nextFloat() {
		return (nextInt() >>> 8) * FLOAT_NORM;
	}
}
