/* Problem 1:
 * 
 * You are given the lengths of three sides of a triangle: a, b, and c. Your task is to calculate the area of a triangle using Heron's formula written below.
 * 
 * The formula for the area A is:
 * A = √[s*(s-a)*(s-b)*(s-c)]
 * where s is the semi-perimeter of the triangle, calculated as : s = (a+b+c)/2 
 * 
 * For example, if the sides of the triangle are 6, 8, 12.
 * The semi-perimeter is (6+8+12)/2=13
 * Area is A=√[13*(13-6)*(13-8)*(13-12)]=√[455]=21.33
 * 
 * Input:
 * Three positive integers a, b, and c representing the lengths of the triangle's sides. Assume, the input is valid.
 * 
 * Output:
 * Area of a triangle, rounded to two decimal places.
 * 
 * 
 * 
 * */

package widener2024_solutions;

import java.util.Scanner;

public class Problem1 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int a = s.nextInt();
		int b = s.nextInt();
		int c = s.nextInt();

		double s1 = (a + b + c) / 2.0;

		double area = Math.sqrt(s1 * (s1 - a) * (s1 - b) * (s1 - c));
		
		System.out.printf("%1.2f", area);
	}

}
