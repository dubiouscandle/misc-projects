package com.dubiouscandle.minineat;

public interface ActivationFunction {
	public float get(float x);

	public static final ActivationFunction ReLU = x -> x <= 0 ? 0 : x;
	public static final ActivationFunction SIGMOID = x -> (float) (1.0 / (1.0 + Math.exp(-x)));
	public static final ActivationFunction SIN = x -> (float) Math.sin(x);
	public static final ActivationFunction TANH = x -> {
		float a = (float) Math.exp(x);
		float b = 1.0f / a;

		return (a - b) / (a + b);
	};
	public static final ActivationFunction IDENTITY = x -> x;
}
