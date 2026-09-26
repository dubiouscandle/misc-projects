import java.util.Scanner;

public class MooOperations {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numTestCases = s.nextInt();

		for (int i = 0; i < numTestCases; i++)
			handle(s.next());

		s.close();
	}

	private static void handle(String string) {
		boolean containsMO = false;
		boolean containsOO = false;
		boolean containsMOO = false;
		boolean containsO = false;

		int stringLength = string.length();

		for (int i = 0; i < stringLength; i++) {
			// only look for OO on the second character
			if (!containsOO && i >= 1 && i + 1 < stringLength) {
				if (string.charAt(i + 1) == 'O' && string.charAt(i) == 'O')
					containsOO = true;

			}

			if (!containsMO && i + 2 < stringLength) {
				if (string.charAt(i) == 'M' && string.charAt(i + 1) == 'O')
					containsMO = true;
			}

			if (!containsO && i > 0 && i < stringLength - 1)
				if (string.charAt(i) == 'O')
					containsO = true;

			if (!containsMOO && i - 1 >= 0 && i + 1 < stringLength)
				if (string.charAt(i - 1) == 'M' && string.charAt(i) == 'O' && string.charAt(i + 1) == 'O')
					containsMOO = true;

			if (containsOO && containsMO && containsMOO && containsO)
				break;
		}

		if (containsMOO) {
			System.out.println(stringLength - 3);
		} else if (containsOO || containsMO) {
			System.out.println(stringLength - 3 + 1);
		} else if (containsO)
			System.out.println(stringLength - 3 + 2);
		else
			System.out.println(-1);

	}
}
