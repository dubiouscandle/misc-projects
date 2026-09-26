package team29;

import java.util.Scanner;

public class Team29Problem5 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		String items = s.nextLine();
		
		s.close();

		int sum = 0;

		for (int i = 0; i < items.length(); i++) {
			if (items.charAt(i) == 'g')
				sum += 1;
			else if (items.charAt(i) == 'y')
				sum += 2;
			else if (items.charAt(i) == 'r')
				sum += 3;
		}
		
		System.out.println(sum);
	}

}
