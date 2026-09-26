import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class EmailFiling {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = in.nextInt(); i >= 1; i--) {
			solution(in);
		}

	}

	static void solution(Scanner in) {
		int m = in.nextInt();
		int n = in.nextInt();
		int k = in.nextInt();

		ArrayList<Integer> arr = new ArrayList<>();
		int[] freqs = new int[n + 1];
		for (int i = 0; i < n; i++) {
			arr.add(in.nextInt());
			freqs[arr.get(i)]++;
		}

		int v = 0;
		int t = 1;
		while (true) {
			int l = t;
			int r = t + k - 1;

			if (arr.size() == 0) {
				System.out.println("YES");
				return;
			}
			if (arr.size() <= k) {
				for (int x : arr) {
					if (!(l <= x)) {
						System.out.println("NO");
						return;
					}
				}

				System.out.println("YES");
				return;
			}
			if (v + k >= arr.size()) {
				v = arr.size() - k;
			}

			boolean removed = false;

			List<Integer> view;

			view = arr.subList(v, v + k);
//			System.out.println(arr + " " + view + " " + l + " " + r);

			Iterator<Integer> iter = view.iterator();
			while (iter.hasNext()) {
				int x = iter.next();

				if (l <= x && x <= r) {
					removed = true;
					freqs[x]--;
					iter.remove();
				}
			}

			if (freqs[t] == 0) {
				if (t + k - 1 < m) {
					t++;
				}
			} else if (!removed) {
				if (v + k < arr.size()) {
					v++;
				} else {
					System.out.println("NO");
					return;
				}
			}
		}

	}

}
