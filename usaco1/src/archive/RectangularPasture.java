package archive;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class RectangularPasture {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		Point[] points = new Point[n];

		int[] distinctXs = new int[n];
		int[] distinctYs = new int[n];

		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();

			points[i] = new Point(x, y);

			distinctXs[i] = x;
			distinctYs[i] = y;
		}

		in.close();

		Arrays.sort(distinctXs);
		Arrays.sort(distinctYs);

		HashMap<Integer, Integer> xCompressionMap = new HashMap<>();
		HashMap<Integer, Integer> yCompressionMap = new HashMap<>();

		for (int i = 0; i < n; i++) {
			xCompressionMap.put(distinctXs[i], i);
			yCompressionMap.put(distinctYs[i], i);
		}

		for (Point point : points) {
			point.x = xCompressionMap.get(point.x);
			point.y = yCompressionMap.get(point.y);
		}

		ArrayList<Point> pointsByY = new ArrayList<>();
		ArrayList<Point> pointsByX = new ArrayList<>();

		for (Point point : points) {
			pointsByY.add(point);
			pointsByX.add(point);
		}

		pointsByY.sort((a, b) -> Integer.compare(a.y, b.y));
		pointsByX.sort((a, b) -> Integer.compare(a.x, b.x));

		long c = 0;
		for (int y1 = 0; y1 < n - 1; y1++) {
			Point lowerPoint = pointsByY.get(y1);
			ArrayList<Integer> cumulativeXs = new ArrayList<>();
			cumulativeXs.add(lowerPoint.x);
			for (int y2 = y1 + 1; y2 < n; y2++) {
				Point upperPoint = pointsByY.get(y2);

				int index = Collections.binarySearch(cumulativeXs, upperPoint.x);
				cumulativeXs.add(index >= 0 ? index : (-index - 1), upperPoint.x);

				int minX = Math.min(lowerPoint.x, upperPoint.x);
				int maxX = Math.max(lowerPoint.x, upperPoint.x);
				
				int lc = Collections.binarySearch(cumulativeXs, minX);
				int rc = cumulativeXs.size() - 1 - Collections.binarySearch(cumulativeXs, maxX);
//				for (Integer x : cumulativeXs) {
//					if (x < minX)
//						lc++;
//					else if (x > maxX)
//						rc++;
//				}

				c += (lc + 1) * (rc + 1);
			}
		}

		System.out.println(c + n + 1);
	}

}
