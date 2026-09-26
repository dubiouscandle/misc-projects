package archive;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchingForSoulmates {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = in.nextInt(); i >= 1; i--) {
			long a = in.nextLong();
			long b = in.nextLong();

			ArrayList<Long> pathA = new ArrayList<Long>();
			pathA.add(a);
			while (a > 1) {
				if (a % 2 == 0)
					a = a >> 1;
				else
					a++;

				pathA.add(a);
			}

			ArrayList<Long> pathB = new ArrayList<Long>();
			pathB.add(b);
			while (b > 1) {
				if (b % 2 == 0)
					b = b >> 1;
				else
					b--;

				pathB.add(b);
			}

			int min = Integer.MAX_VALUE;
			for (int j = 0; j < pathA.size(); j++) {
				int index = binarySearch(pathB, pathA.get(j));
				if (pathA.get(j) > pathB.get(index))
					continue;

				int dist = (int) (j + index + pathB.get(index) - pathA.get(j));

				if (dist < min)
					min = dist;
			}

			System.out.println(min);
		}

		in.close();
	}

	public static int binarySearch(ArrayList<Long> arr, long n) {
		int max = arr.size() - 1;
		int min = 0;

		while (min < max) {
			int mid = (min + max + 1) / 2;

			boolean works = arr.get(mid) >= n;

			if (works) {
				min = mid;
			} else {
				max = mid - 1;
			}
		}

		return min;
	}

}
