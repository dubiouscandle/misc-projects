import java.util.Random;
import java.util.Scanner;

public class Decrypt {

	public static void main(String[] args) {
		StringBuilder input = new StringBuilder();
		Random random = new Random();
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for (int i = 0; i < 500; i++) {
			int letters = random.nextInt(1, 2);
			for (int j = 0; j < letters; j++) {
				input.append(chars[random.nextInt(chars.length)]);
			}
			input.append(361235);
		}
		System.out.println(input);

		Scanner in = new Scanner(System.in);

		String encrypted = in.next();

		in.close();

		StringBuilder out = new StringBuilder();
		String[] numbers = encrypted.split("[a-zA-Z]");

		for (String string : numbers) {
			if (string.length() == 0) {
				continue;
			}

			long value = Long.parseLong(string);
			out.append((char) ((int) (value % 26) + 'A'));
		}

		System.out.println(out);

	}

}
