package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TargetPracticeII {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = in.nextInt(); i >= 1; i--) {
			solution(in);
		}
	}

	public static void solution(Scanner in) {
		int n = in.nextInt();
		long xA = in.nextLong();

		Rect[] rects = new Rect[n];
		for (int i = 0; i < n; i++) {
			rects[i] = new Rect(in.nextLong(), in.nextLong(), in.nextLong());
		}

		ArrayList<Long> pSlopes = new ArrayList<>();
		ArrayList<Long> nSlopes = new ArrayList<>();

		for (int i = 0; i < 4 * n; i++) {
			long next = in.nextLong();

			if (next < 0)
				nSlopes.add(next);
			else
				pSlopes.add(next);
		}

		Arrays.sort(rects);

		for (int i = 0;; i++) {
			if (works(i, xA, rects, pSlopes, nSlopes)) {
				System.out.println(i);
				return;
			}
		}
	}

	private static boolean works(int d, long xA, TargetPracticeII.Rect[] rects, ArrayList<Long> pSlopes,
			ArrayList<Long> nSlopes) {
		int n = rects.length;

		return false;
	}

	public static class Rect {
		public long xB, yA, yB;

		public Rect(long yA, long yB, long xB) {
			super();
			this.xB = xB;
			this.yA = yA;
			this.yB = yB;
		}
	}

}
