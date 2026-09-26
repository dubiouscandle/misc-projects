/* Problem 5
 * 
 * Yard sale items are marked with green, yellow, and red cards to indicate their prices. Each item costs:
 * $1 for green-marked items
 * $2 for yellow-marked items
 * $3 for red-marked items
 * 
 * Your task is to calculate the total price paid based on a list of items purchased.
 * 
 * Input (assume the input is valid):
 * A string of characters (g, y, and r) representing the items a customer bought at the yard sale. 
 * Each character corresponds to an item color, with g for green, y for yellow, and r for red. The string's length represents the total number of items bought.
 * 
 * Output:
 * The total price paid by the customer.
 * 
 * 
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem5 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		String items = s.nextLine();

		int sum = 0;

		for (int i = 0; i < items.length(); i++) {
			char currentChar = items.charAt(i);
			
			if(currentChar == 'g')
				sum += 1;
			else if(currentChar == 'y')
				sum += 2;
			else if(currentChar == 'r')
				sum += 3;
		}
		
		System.out.println(sum);

	}

}
