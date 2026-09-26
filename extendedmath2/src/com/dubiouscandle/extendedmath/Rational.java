package com.dubiouscandle.extendedmath;

public class Rational {
	public final int numer, denom;

	public Rational(int val) {
		this(val, 1);
	}

	public Rational(int numer, int denom) {
		this.numer = numer;
		this.denom = denom;
	}


	public int intValue() {
		return numer / denom;
	}

	public double doubleValue() {
		return (double) numer / denom;
	}

}
