package com.dubiouscandle.connect4solver;

import java.util.Arrays;

public class TranspositionTable {
	private long[] keyValues;

	public TranspositionTable(int size) {
		keyValues = new long[size];
	}

	public void put(long key, int val) {
		keyValues[index(key)] = key << 8 | val;
	}

	public int get(long key) {
		int index = index(key);
		long keyValue = keyValues[index];
		
		
		if ((keyValue >>> 8) != key) {
			return 0;
		}

		return ((int) keyValue) & 255;
	}

	private int index(long key) {
		return Math.abs(Long.hashCode(key)) % keyValues.length;
	}

	public void reset() {
		Arrays.fill(keyValues, 0);
	}
}
