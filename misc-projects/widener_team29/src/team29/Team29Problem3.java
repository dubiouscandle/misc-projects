package team29;

import java.util.Scanner;

public class Team29Problem3 {

	public static void main(String[] args) {
		System.out.println("e");
		
		Scanner s = new Scanner(System.in);

		int numItems = s.nextInt();
		int discountCard = s.nextInt();
		double price = s.nextDouble();

		s.close();

		double total = numItems * price;
		
		if(discountCard == 1)
			total *= 0.8;
		
		System.out.printf("1.2f", total);
	}

	
	
}
