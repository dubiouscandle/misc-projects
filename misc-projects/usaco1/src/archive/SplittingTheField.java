package archive;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SplittingTheField {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		int n = in.nextInt();
		Point[] points = new Point[n];

		long bbxMin = Long.MAX_VALUE;
		long bbxMax = Long.MIN_VALUE;
		long bbyMin = Long.MAX_VALUE;
		long bbyMax = Long.MIN_VALUE;

		for (int i = 0; i < n; i++) {
			points[i] = new Point(in.nextLong(), in.nextLong());

			bbxMin = Math.min(bbxMin, points[i].x);
			bbxMax = Math.max(bbxMax, points[i].x);
			bbyMin = Math.min(bbyMin, points[i].y);
			bbyMax = Math.max(bbyMax, points[i].y);
		}

		long bbArea = (bbxMax - bbxMin) * (bbyMax - bbyMin);

		long minArea = Long.MAX_VALUE;

		{
			Arrays.sort(points, (a, b) -> Long.compare(a.x, b.x));

			long[] yMinsL = new long[n];
			long[] yMinsR = new long[n];
			long[] yMaxsL = new long[n];
			long[] yMaxsR = new long[n];

			for (int i = 0; i < n; i++) {
				Point cur = points[i];
				yMinsL[i] = i == 0 ? cur.y : Math.min(cur.y, yMinsL[i - 1]);
				yMaxsL[i] = i == 0 ? cur.y : Math.max(cur.y, yMaxsL[i - 1]);
			}
			for (int i = n - 1; i >= 0; i--) {
				Point cur = points[i];
				yMinsR[i] = (i == n - 1) ? cur.y : Math.min(cur.y, yMinsL[i + 1]);
				yMaxsR[i] = (i == n - 1) ? cur.y : Math.min(cur.y, yMaxsL[i + 1]);
			}

			for (int i = 0; i < n - 1; i++) {
				int j = i + 1;

				if (points[i].x == points[j].x)
					continue;

				long area = (points[i].x - bbxMin) * (yMaxsL[i] - yMinsL[i])
						+ (bbxMax - points[j].x) * (yMaxsR[j] - yMinsR[j]);

				if (area < minArea) {
					minArea = area;
					System.out.println(i + " " + j);
				}
			}
		}

		for (Point p : points) {
			long x = p.x;
			p.x = p.y;
			p.y = x;
		}

		{
			Arrays.sort(points, (a, b) -> Long.compare(a.x, b.x));

			long[] yMinsL = new long[n];
			long[] yMinsR = new long[n];
			long[] yMaxsL = new long[n];
			long[] yMaxsR = new long[n];

			for (int i = 0; i < n; i++) {
				Point cur = points[i];
				yMinsL[i] = i == 0 ? cur.y : Math.min(cur.y, yMinsL[i - 1]);
				yMaxsL[i] = i == 0 ? cur.y : Math.max(cur.y, yMaxsL[i - 1]);
			}
			for (int i = n - 1; i >= 0; i--) {
				Point cur = points[i];
				yMinsR[i] = (i == n - 1) ? cur.y : Math.min(cur.y, yMinsL[i + 1]);
				yMaxsR[i] = (i == n - 1) ? cur.y : Math.min(cur.y, yMaxsL[i + 1]);
			}

			for (int i = 0; i < n - 1; i++) {
				int j = i + 1;

				if (points[i].x == points[j].x)
					continue;

				long area = (points[i].x - bbxMin) * (yMaxsL[i] - yMinsL[i])
						+ (bbxMax - points[j].x) * (yMaxsR[j] - yMinsR[j]);

				if (area < minArea)
					minArea = area;
			}
		}

		out.print(bbArea - minArea);
		out.flush();
	}

	public static class Point {
		public long x, y;

		public Point(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}
