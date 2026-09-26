
package silver_january2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SearchingForSoulmates {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int t = s.nextInt();

		for (int i = 0; i < t; i++) {
			System.out.println(getNumMoves(s.nextLong(), s.nextLong()));
		}

		s.close();
	}

	private static int getNumMoves(long n, long p) {
		HashSet<Long> seen = new HashSet<>();
		ArrayList<Long> layer = new ArrayList<>();

		seen.add(n);
		layer.add(n);

		int numMoves = -1;
		boolean found = false;

		while (!found) {
			ArrayList<Long> next = new ArrayList<>();

			for (int i = 0; i < layer.size(); i++) {
				long value = layer.get(i);

				if (value == p) {
					found = true;
					break;
				}

				if (!seen.contains(value + 1)) {
					next.add(value + 1);
					seen.add(value + 1);
				}
				if (!seen.contains(value * 2)) {
					next.add(value * 2);
					seen.add(value * 2);
				}
				if (value % 2 == 0 && !seen.contains(value / 2)) {
					next.add(value / 2);
					seen.add(value / 2);
				}

			}
			
			System.out.println(next);

			layer = next;

			numMoves++;
		}

		return numMoves;
	}
}
