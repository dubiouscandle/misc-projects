package archive;


import java.util.ArrayList;
import java.util.Scanner;

public class SubsetEquality {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		String s = in.next();
		String t = in.next();

		int q = in.nextInt();
		String[] strings = new String[q];
		boolean[] works = new boolean[q];
		for (int i = 0; i < q; i++) {
			String cur = in.next();

			boolean found = false;
			for (int j = i - 1; j >= 0; j--) {
				if (isSubset(strings[j], cur)) {
					found = true;

					if (!works[j]) {
						works[i] = false;
					} else {
						works[i] = works(s, t, cur);
					}

					break;
				}
			}

			if (!found) {
				works[i] = works(s, t, cur);
			}

			strings[i] = cur;
		}

		for (int i = 0; i < works.length; i++) {
			if (works[i])
				System.out.print('Y');
			else
				System.out.print('N');
		}
	}

	public static boolean works(String s, String t, String q) {
		StringBuilder sSB = new StringBuilder();
		StringBuilder tSB = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			if (q.contains(s.charAt(i) + "")) {
				sSB.append(s.charAt(i));
			}
			if (q.contains(t.charAt(i) + "")) {
				tSB.append(t.charAt(i));
			}
		}

		return sSB.toString().equals(tSB.toString());
	}

	// returns if a is subset of b
	// if all chars of a is within b
	public static boolean isSubset(String a, String b) {
		for (int i = 0; i < a.length(); i++) {
			if (!b.contains(a.charAt(i) + "")) {
				return false;
			}
		}
		return true;
	}

}
