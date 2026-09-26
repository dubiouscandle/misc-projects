package main;

import java.util.ArrayList;
import java.util.Scanner;

public class SyllableCounting {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 10; i++) {
			solution(in.nextLine());
		}
	}

	private static void solution(String line) {
		String[] words = line.split(" ");

		int count = 0;
		for (String word : words) {
			ArrayList<String> groups = getVowelGroups(word);

			for (String group : groups) {
				count++;
			}

			if (word.charAt(word.length() - 1) == 'e' || word.charAt(word.length() - 1) == 'E') {
				if (groups.size() > 1 && groups.get(groups.size() - 1).length() == 1) {
					count--;
				}
			}
		}

		System.out.println(words.length + " " + count);
	}

	static ArrayList<String> getVowelGroups(String word) {
		ArrayList<String> vowelGroups = new ArrayList<>();
		String group = "";

		for (char c : word.toCharArray()) {
			if (isVowel(c) || ((c == 'Y' || c == 'y') && group.equals(""))) {
				group += c;
			} else {
				if (!group.equals("")) {
					vowelGroups.add(group);
				}
				group = "";
			}
		}

		if (!group.equals("")) {
			vowelGroups.add(group);
		}

		return vowelGroups;
	}

	static boolean isVowel(char c) {
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O'
				|| c == 'U';

	}

}
