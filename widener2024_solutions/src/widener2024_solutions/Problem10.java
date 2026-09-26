/* Problem 10
 * 
 * Edwin needs to measure the pumping rate of two water pumps. To do so, he used them to pump water into a tank and checked how much water was pumped
 * into the tank in a specific time.
 * 
 * He found that the first pump pumps a gallons of water in b seconds and the second pump pumps c gallons of water in d seconds. He also discovered
 * that when both pumps are used at the same time, together they pump b gallons of water in d seconds.
 * 
 * Unfortunately, Edwin has spilled some water on his records and can't recover the values a and c. He remembers that they were positive integers.
 * Now he needs to know how many possible combinations of a and c are consistent with his measurements.
 * 
 * Input (assume input is valid): b and d
 * Output: the number of possible combinations of a and c
 * 
 * 
 * 
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem10 {
	//a = (b^2 - bc)/d
	// b > c
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		int b = s.nextInt();
		int d = s.nextInt();
		
		int c = 1;
		int counter = 0;
		
		while(b > c) {
			if((b * b - b * c) % d == 0) {
				counter++;
			}
			
			c++;
		}
		
		System.out.println(counter);
	}

}
