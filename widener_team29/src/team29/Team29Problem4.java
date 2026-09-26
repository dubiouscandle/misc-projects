package team29;

import java.util.Scanner;

public class Team29Problem4 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		double price = s.nextDouble();
		int numBottles = s.nextInt();
		
		s.close();

		System.out.println(price / numBottles);
		if (price / numBottles <= 0.75) {
			System.out.println("yes");
		} else
			System.out.println("no");

	}

}
