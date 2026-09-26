package com.dubiouscandle.graphgame.util;

public class EnumFloatMap<T extends Enum<T>> {
	private final float[] values;

	public EnumFloatMap(Class<T> enumClass, float defaultValue) {
		this.values = new float[enumClass.getEnumConstants().length];

		for (int i = 0; i < values.length; i++) {
			values[i] = defaultValue;
		}
	}

	public EnumFloatMap(Class<T> enumClass) {
		this(enumClass, 0);
	}

	public float get(T e) {
		return values[e.ordinal()];
	}

	public void set(T e, float value) {
		values[e.ordinal()] = value;
	}

	public void add(T e, float value) {
		values[e.ordinal()] = value;
	}

	public void increment(T e) {
		values[e.ordinal()]++;
	}

}
