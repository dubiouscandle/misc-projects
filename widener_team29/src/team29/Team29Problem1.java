package team29;

import java.util.Scanner;

public class Team29Problem1 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int a = s.nextInt();
		int b = s.nextInt();
		int c = s.nextInt();

		double s1 = (a + b + c) / 2.0;

		double area = Math.sqrt(s1 * (s1 - a) * (s1 - b) * (s1 - c));

		System.out.printf("1.2f", area);
		
		s.close();

	}

}
