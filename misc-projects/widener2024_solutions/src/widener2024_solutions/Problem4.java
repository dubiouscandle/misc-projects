/* Problem 4:
 * 
 * A person is shopping for water bottles and wants to buy a pack where each bottle costs $0.75 or less. 
 * Write a program that reads the total price of the pack of water bottles and the number of bottles it contains. 
 * The program should calculate the price of one bottle and determine whether the person will purchase the pack. 
 * Additionally, the program should print the price per bottle.
 * 
 * Input (assume the input is valid):
 * 1. Total price of the pack of water bottles
 * 2. The number of bottles in the pack (an integer greater than 1)
 * 
 * Output:
 * The price per bottle rounded to two decimal places
 * "YES" if the person will buy the pack of water bottles, or "NO" if the person will not buy the pack.
 * 
 * 
 * 
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem4 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		double totalPrice = s.nextDouble();
		int numBottles = s.nextInt();
		
		double pricePerBottle = totalPrice/numBottles;
		
		System.out.printf("%1.2f ", pricePerBottle);
		
		if(pricePerBottle <= 0.75) {
			System.out.println("YES");
		}
		else
			System.out.println("NO");

	}

}
