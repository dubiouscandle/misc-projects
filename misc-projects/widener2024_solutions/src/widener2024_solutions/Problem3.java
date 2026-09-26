/* Problem 3:
 * 
 * You are running a grocery store and need to calculate the total price based on the number of items bought and whether the customer has a discount card.
 * If the customer has a discount card, they get a 20% discount on the total price.
 * 
 * Input (assume the input is valid):
 * 1. The number of items purchased (positive integer between 1 and 100)
 * 2. Discount card indicator, an integer 1 (has discount card) or 0 (doesn't have discount card).
 * 3. Price of the item - assume that all items in the store have the same prices.
 * 
 * Output:
 * Total price in dollars, rounded to two decimal places.
 * 
 * 
 * 
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem3 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		int numItemsPurchased = s.nextInt();
		int indicator = s.nextInt();
		double pricePerItem = s.nextDouble();
		
		double totalPrice = numItemsPurchased * pricePerItem;
		
		if(indicator == 1)
			totalPrice = totalPrice * 0.8;
		
		
		System.out.printf("%1.2f", totalPrice);
	}

}
