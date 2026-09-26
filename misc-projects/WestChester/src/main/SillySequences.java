package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SillySequences {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 10; i++) {
			solution(in);
		}
	}

	//highest ar^n
	
	private static void solution(Scanner in) {
		Rational[] nums = new Rational[3];

		nums[0] = new Rational(in.next());
		nums[1] = new Rational(in.next());
		nums[2] = new Rational(in.next());

		Arrays.sort(nums);

		Rational max = new Rational(Integer.MIN_VALUE, 1);
		
		Rational[] ratios = { 
				getRatio(nums[0], nums[1], nums[2]), 
				getRatio(nums[0], nums[2], nums[1]),
				getRatio(nums[1], nums[2], nums[0]), 
				getRatio(nums[1], nums[0], nums[2]),
				getRatio(nums[2], nums[0], nums[1]), 
				getRatio(nums[2], nums[1], nums[0]), };

		for (int i = 0; i < 6; i++) {
			if (ratios[i] == null)
				continue;

			if (ratios[i].compareTo(max) > 0) {
				max = ratios[i];
			}
		}

		System.out.println(max.simplify());
	}

	private static Rational getRatio(Rational rational1, Rational rational2, Rational rational3) {
		Rational r = new Rational(rational1.b * rational2.a, rational1.a * rational2.b);

		if (new Rational(rational2.a * r.a, rational2.b * r.b).compareTo(rational3) == 0) {
			return r;
		}

		return null;
	}

	static class Rational implements Comparable<Rational> {
		final int a, b;

		public Rational(int a, int b) {
			super();
			this.a = a;
			this.b = b;
		}

		public Rational(String word) {
			super();

			if (!word.contains("/")) {
				a = Integer.parseInt(word);
				b = 1;
			} else {
				String[] split = word.split("/");
				a = Integer.parseInt(split[0]);
				b = Integer.parseInt(split[1]);
			}
		}

		@Override
		public String toString() {
			if (b == 1) {
				return "" + a;
			} else
				return a + "/" + b;
		}

		@Override
		public int compareTo(Rational o) {
			return Double.compare((double) a / b, (double) o.a / o.b);
		}

		private Rational simplify() {
			ArrayList<Integer> commonDivisors = new ArrayList<>();

			int sign = a * b < 0 ? -1 : 1;
			
			int a = this.a;
			int b = this.b;
			
			a = Math.abs(a);
			b = Math.abs(b);
			
			for (int i = 2; i <= Math.min(a, b); i++) {
				if (a % i == 0 && b % i == 0) {
					a /= i;
					b /= i;
					commonDivisors.add(i);
					i = 1;
				}
			}

			a = this.a;
			b = this.b;

			for (int div : commonDivisors) {
				a /= div;
				b /= div;
			}
			return new Rational(a, b);
		}
	}

	// 1/2401 1/49 -1/343
}
