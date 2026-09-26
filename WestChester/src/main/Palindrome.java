package main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Palindrome {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 5; i++) {
			solution(in);
		}

	}

	private static void solution(Scanner in) {
		String originalLine = in.nextLine();
		String line = "";

		for (int i = 0; i < originalLine.length(); i++) {
			if (Character.isAlphabetic(originalLine.charAt(i))) {
				line += originalLine.charAt(i);
			}
		}
		
		line = line.toLowerCase();

		HashSet<String> substrs = new HashSet<>();

		for (int i = 0; i < line.length(); i++) {
			for (int j = i + 1; j <= line.length(); j++) {
				substrs.add(line.substring(i, j));
			}
		}

		boolean found = false;
		Iterator<String> iter = substrs.iterator();

		while (iter.hasNext()) {
			String next = iter.next();
			if (next.length() >= 3 && isPalindrome(next)) {
				found = true;
				System.out.print(next + " ");
			}
		}
		
		if(!found) {
			System.out.print("NONE");
		}
		System.out.println();
	}

	static boolean isPalindrome(String string) {
		int i = 0;
		int j = string.length() - 1;

		while (i < j) {
			if (string.charAt(i) != string.charAt(j)) {
				return false;
			}

			i++;
			j--;
		}

		return true;
	}

}
