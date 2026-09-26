package main;

import java.util.Scanner;

public class Infix {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < 5; i++) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		System.out.println(parse(in.nextLine()));
	}

	private static int parse(String string) {
		for(char c : string.toCharArray()) {
			
		}
		
		return null;
	}

}
