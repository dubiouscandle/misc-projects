
import java.util.Scanner;

public class CountingLiars {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int numCows = s.nextInt();

		char[] dir = new char[numCows];
		int[] locations = new int[numCows];

		for (int i = 0; i < numCows; i++) {
			dir[i] = s.next().charAt(0);
			locations[i] = s.nextInt();
		}

		s.close();

		int minLiars = Integer.MAX_VALUE;

		for (int i = 0; i < numCows; i++) {
			int location = locations[i];
			int numLiars = 0;

			for (int j = 0; j < numCows; j++) {
				if (dir[j] == 'G') {
					if (locations[j] > location) {
						numLiars++;
					}
				} else if (dir[j] == 'L') {
					if (locations[j] < location) {
						numLiars++;
					}
				}
			}

			if (numLiars < minLiars) {
				minLiars = numLiars;
			}

		}
		
		System.out.println(minLiars);
	}

}
