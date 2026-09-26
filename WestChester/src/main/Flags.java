package main;

import java.util.HashMap;
import java.util.Scanner;

public class Flags {
	static String[] pairs = new String[] { "DL", "DO", "DH", "DU", "HD", "OD", "LD", "ALO", "ALU", "OU", "UL", "HL",
			"OL", "LL", "AHO", "UO", "HO", "OO", "LO", "UH", "HH", "LU", "OAH", "LAH", "OH", "OAL", };

	static HashMap<Character, String> map1 = new HashMap<>();
	static HashMap<String, Character> map2 = new HashMap<>();

	public static void main(String[] args) {
		for (char i = 'A'; i <= 'Z'; i++) {
			map1.put(i, pairs[i - 'A']);
			map2.put(pairs[i - 'A'], i);
		}

		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 5; i++) {
			solution1(in);
		}
		for (int i = 0; i < 5; i++) {
			solution2(in);
		}
	}

	private static void solution1(Scanner in) {
		String next = in.nextLine();

		String out = "";

		for (int i = 0; i < next.length();) {
			System.out.println(i);
			String substr2 = next.substring(i, i + 2);

			if (next.charAt(i) == '#') {
				out += ' ';
				i++;
			} else if (map2.containsKey(substr2)) {
				out += map2.get(substr2);
				i += 2;
			} else {
				String substr3 = next.substring(i, i + 3);

				if (map2.containsKey(substr3)) {
					out += map2.get(substr3);
					i += 3;
				}
			}
		}

		System.out.println(out);
	}

	private static void solution2(Scanner in) {
		String next = in.nextLine();

		String out = "";

		for (char c : next.toCharArray()) {
			if (c == ' ') {
				out += '#';
			} else {
				out += map1.get(c);
			}
		}

		System.out.println(out);
	}

}
