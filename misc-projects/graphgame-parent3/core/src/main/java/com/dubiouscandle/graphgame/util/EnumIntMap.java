package com.dubiouscandle.graphgame.util;

public class EnumIntMap<T extends Enum<T>> {
	private final int[] values;

	public EnumIntMap(Class<T> enumClass, int defaultValue) {
		this.values = new int[enumClass.getEnumConstants().length];

		for (int i = 0; i < values.length; i++) {
			values[i] = defaultValue;
		}
	}

	public EnumIntMap(Class<T> enumClass) {
		this(enumClass, 0);
	}

	public int get(T e) {
		return values[e.ordinal()];
	}

	public void set(T e, int value) {
		values[e.ordinal()] = value;
	}

	public void add(T e, int value) {
		values[e.ordinal()] += value;
	}

	public void increment(T e) {
		values[e.ordinal()]++;
	}

}
