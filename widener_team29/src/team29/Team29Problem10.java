package team29;

import java.util.Scanner;

public class Team29Problem10 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int b = s.nextInt();
		int d = s.nextInt();
		
		s.close();

		int counter = 0;

		int c = 1;

		while (b * b - c * b > 0) {

			if (b * b - c * b == 0)
				break;

			if ((b * b - c * b) % d == 0)
				counter++;
			
			c++;
			
		}

		System.out.println(counter);
		
	}

}
