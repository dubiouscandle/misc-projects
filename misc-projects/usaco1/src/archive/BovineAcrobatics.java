package archive;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class BovineAcrobatics {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		long m = in.nextInt();
		long k = in.nextInt();

		TreeMap<Long, Long> map = new TreeMap<>();
		Pair[] pairs = new Pair[n];
		for (int i = 0; i < n; i++) {
			pairs[i] = new Pair(in.nextLong(), Math.min(m, in.nextLong()));
		}

		map.put(Long.MAX_VALUE, m);

		Arrays.sort(pairs);

		long sum = 0;
		for (int i = 0; i < n; i++) {
			Pair cur = pairs[i];

			while (true) {
				Entry<Long, Long> last = map.lastEntry();

				long w = last.getKey();
				long c = last.getValue();

				if (cur.w + k > w) {
					break;
				}

				if (cur.c >= c) {
					map.pollLastEntry();
					cur.c -= c;

					if (map.containsKey(cur.w)) {
						map.put(cur.w, map.get(cur.w) + c);
					} else {
						map.put(cur.w, c);
					}

					sum += c;
				} else {
					map.put(last.getKey(), last.getValue() - cur.c);
					sum += cur.c;
					if (map.containsKey(cur.w)) {
						map.put(cur.w, map.get(cur.w) + cur.c);
					} else {
						map.put(cur.w, cur.c);
					}
					break;
				}
			}

		}

		System.out.println(sum);
	}

	public static class Pair implements Comparable<Pair> {
		public long w, c;

		public Pair(long w, long c) {
			super();
			this.w = w;
			this.c = c;
		}

		@Override
		public int compareTo(BovineAcrobatics.Pair o) {
			return -Long.compare(w, o.w);
		}
	}
}
