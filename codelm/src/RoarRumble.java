import java.util.Random;
import java.util.Scanner;

public class RoarRumble {
	public static void main(String[] args) {
		{
			StringBuilder input = new StringBuilder();
			Random random = new Random();
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			for (int i = 0; i < 500; i++) {
				input.append(chars[random.nextInt(chars.length)]);
			}
			System.out.println("roar1=\"" + input + "\"");
		}
		{
			StringBuilder input = new StringBuilder();
			Random random = new Random();
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			for (int i = 0; i < 500; i++) {
				input.append(chars[random.nextInt(chars.length)]);
			}
			System.out.println("roar2=\"" + input + "\"");
		}
		Scanner in = new Scanner(System.in);

		String roar1 = in.next();
		String roar2 = in.next();

		in.close();

		int strength1 = getStrength(roar1);
		int strength2 = getStrength(roar2);

		System.out.println(Math.max(strength1, strength2));
	}

	public static int getStrength(String roar) {
		int points = 0;

		for (int i = 0; i < roar.length(); i++) {
			if (isVowel(roar.charAt(i))) {
				if (i > 0 && isVowel(roar.charAt(i - 1))) {
					points += 3;
				} else {
					points += 1;
				}
			}
		}

		return points;
	}

	public static boolean isVowel(char c) {
		c = Character.toLowerCase(c);

		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
	}
}
