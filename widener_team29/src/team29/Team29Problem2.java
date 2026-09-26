package team29;

import java.util.Scanner;

public class Team29Problem2 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		double fuel = s.nextDouble();
		double eff = s.nextDouble();
		
		s.close();

		System.out.printf("%1.2f", fuel / eff);
	}
}
