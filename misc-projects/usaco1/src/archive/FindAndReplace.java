package archive;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class FindAndReplace {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		for (int i = in.nextInt(); i >= 1; i--) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		String stringA = in.next();
		String stringB = in.next();

		int[] edges = new int[52];
		int[] inDegrees = new int[52];

		Arrays.fill(edges, -1);

		HashSet<Integer> u = new HashSet<Integer>();
		for (int i = 0; i < stringA.length(); i++) {
			int a = index(stringA.charAt(i));
			int b = index(stringB.charAt(i));
			u.add(b);
			if (edges[a] != b && edges[a] != -1) {
				System.out.println(-1);
				return;
			}

			if (edges[a] != b)
				inDegrees[b]++;

			edges[a] = b;
		}

		for (int i = 0; i < 52; i++) {
			if (edges[i] == -1)
				edges[i] = i;
		}

		int oneCycles = 0;
		boolean[] seen = new boolean[52];
		for (int i = 0; i < 52; i++) {
			if (edges[i] == i) {
				oneCycles++;
				seen[i] = true;
				continue;
			}

			if (inDegrees[i] != 0 || seen[i])
				continue;

			int cur = i;

			while (!seen[cur]) {
				seen[cur] = true;

				cur = edges[cur];

			}
		}

//		for (int i = 0; i < 26; i++) {
//			System.out.println((char) (i + 'A') + " " + seen[i]);
//		}

		int c = 0;
		for (int i = 0; i < 52; i++) {
			if (seen[i])
				continue;

			int cur = i;
			while (!seen[cur]) {
				seen[cur] = true;

				cur = edges[cur];
			}
			c++;
		}

		if (u.size() == 52 && c > 0) {
			System.out.println(-1);
			return;
		}
		System.out.println(52 - oneCycles + c);

	}

	public static int index(char c) {
		if (c <= 'Z')
			return c - 'A' + 26;
		else
			return c - 'a';
	}

}
