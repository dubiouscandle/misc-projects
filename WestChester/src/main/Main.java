package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 5; i++) {
			solution(in.next().toCharArray());
		}
	}

	public static void solution(char[] in) {
		char[] line = in.clone();
		for (int j = 0; j < line.length; j++) {
			if (line[j] == '*') {
				line[j] = ' ';
			}
		}

		System.out.println(new String(line));
		boolean running = true;

		while (running) {
			char[] prev = line.clone();

			for (int i = 0; i < line.length; i++) {
				if (prev[i] == '/') {
					line[i] = '_';
					if (i + 1 < line.length && prev[i + 1] == '|') {
						line[i + 1] = '/';
					}
				}
			}

			boolean equals = true;
			for (int i = 0; i < line.length; i++) {
				if (line[i] != prev[i]) {
					equals = false;
				}
			}
			if (equals) {
				running = false;
			} else
				System.out.println(new String(line));

		}
	}
}
