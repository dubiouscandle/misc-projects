/* Problem 2:
 * 
 * You are planning a road trip and want to calculate the fuel needed based on the distance you will travel and your vehicle's fuel efficiency.
 * 
 * Input (assume the input is valid):
 * 1. Trip Distance: The distance of the trip in miles
 * 2. Fuel Efficiency: The vehicle's efficiency in miles per gallon (mpg).
 * 
 * Output: 
 * Total Fuel Needed: The total amount of fuel required for the trip, expressed in gallons and rounded to two decimal places.
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem2 {
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		double distance = s.nextDouble();
		double eff = s.nextDouble();
		
		System.out.printf("%1.2f", distance / eff);
	}
	
}
