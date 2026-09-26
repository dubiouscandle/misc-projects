package archive;
import java.util.HashSet;
import java.util.Scanner;

public class Cowlender {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		HashSet<Long> set = new HashSet<>();

		for (int i = 0; i < n; i++) {
			set.add(in.nextLong());
		}

		long min = Long.MAX_VALUE;
		for (long x : set) {
			if (x < min) {
				min = x;
			}
		}
		long maxL = min / 4;

		if (set.size() <= 3) {
			System.out.println(maxL * (maxL + 1) / 2);
			return;
		}

		HashSet<Long> diffs = new HashSet<>();

		for (Long x : set) {
			for (Long y : set) {
				if (x == y)
					continue;

				diffs.add(Math.abs(x - y));
			}
		}

		HashSet<Long> candidates = new HashSet<>();
		for (long diff : diffs) {
			for (long i = 1; i * i <= diff; i++) {
				if (diff % i == 0) {
					candidates.add(i);
					candidates.add(diff / i);
				}
			}
		}

		long sum = 0;

		for (long candidate : candidates) {
			if (candidate > maxL) {
				continue;
			}

			HashSet<Long> remainders = new HashSet<>();
			for (long x : set) {
				remainders.add(x % candidate);
				if (remainders.size() >= 4) {
					break;
				}
			}
			if (remainders.size() <= 3) {
				sum += candidate;
			}
		}

		System.out.println(sum);
	}

}
