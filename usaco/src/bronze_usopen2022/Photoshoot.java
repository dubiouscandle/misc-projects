package bronze_usopen2022;

import java.util.Scanner;

public class Photoshoot {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numCows = s.nextInt();
		String cows = s.next();

		for (int i = numCows - 1; i >= 0; i -= 2) {
			if (cows.charAt(i) == 'G')
				continue;

			if (cows.charAt(0) == 'H')
				continue;

			for (int j = 0; j <= i / 2; j++) {
				
			}
		}
	}

}
