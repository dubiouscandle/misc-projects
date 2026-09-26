package archive;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class StuckInARut {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		ArrayList<Cow> nCows = new ArrayList<>();
		ArrayList<Cow> eCows = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			if (in.next().charAt(0) == 'N') {
				nCows.add(new Cow(in.nextLong(), in.nextLong()));
			} else {
				eCows.add(new Cow(in.nextLong(), in.nextLong()));
			}
		}

		ArrayList<ContactPoint> contactPoints = new ArrayList<>();

		for (Cow nCow : nCows) {
			for (Cow eCow : eCows) {

			}
		}
	}

	static class Cow extends Point {
		int blame = 0;

		public Cow(long x, long y) {
			super(x, y);
		}

	}

	static class ContactPoint extends Point implements Comparable<ContactPoint> {
		long t;
		Cow nCow, eCow, blockedCow;

		public ContactPoint(Cow nCow, Cow eCow) {
			long x = nCow.x;
			long y = eCow.y;

			long eTime = x - eCow.x;
			long nTime = y - nCow.y;
			t = Math.max(eTime, nTime);

			if (eTime == nTime) {
				t = -1;
			}else if(eTime > nTime) {
				blockedCow
			}

			this.nCow = nCow;
			this.eCow = eCow;

			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(StuckInARut.ContactPoint o) {
			return Long.compare(t, o.t);
		}
	}

	static class Point {
		long x, y;

		public Point() {
		}

		public Point(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

}
