package main;

import java.util.Scanner;

public class Cipher {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 5; i++) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		String line = in.nextLine();
		int n = in.nextInt();
		
		int[] arr = new int[n];

		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}

		in.nextLine();
		
		String output = "";

		int shiftIndex = 0;
		for (char c : line.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				int shift = arr[shiftIndex];
				output += shift(c, shift);
				shiftIndex++;
				shiftIndex %= arr.length;
			} else {
				output += c;
			}
		}

		System.out.println(output);
	}

	private static char shift(char c, int shift) {
		if (Character.isUpperCase(c)) {
			int norm = c - 'A';

			norm += shift;

			norm = norm % 26;
			if (norm < 0)
				norm += 26;

			return (char) (norm + 'A');
		} else if (Character.isLowerCase(c)) {
			int norm = c - 'a';

			norm += shift;

			norm = norm % 26;
			if (norm < 0)
				norm += 26;

			return (char) (norm + 'a');
		}

		return '+';
	}

}
