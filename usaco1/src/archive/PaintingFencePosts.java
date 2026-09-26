package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class PaintingFencePosts {
	static long perimeter = 0;
	static Post last;
	static Post first;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int p = in.nextInt();

		HashMap<Long, ArrayList<Post>> rows = new HashMap<>();
		HashMap<Long, ArrayList<Post>> cols = new HashMap<>();

		ArrayList<Post> arr = new ArrayList<>();
		Post[] posts = new Post[p];
		for (int i = 0; i < p; i++) {
			posts[i] = new Post(in.nextLong(), in.nextLong(), i);
			arr.add(posts[i]);
			rows.put(posts[i].y, new ArrayList<>());
			cols.put(posts[i].x, new ArrayList<>());
		}

		for (int i = 0; i < p; i++) {
			rows.get(posts[i].y).add(posts[i]);
			cols.get(posts[i].x).add(posts[i]);
		}
		for (ArrayList<Post> row : rows.values()) {
			row.sort((a, b) -> Long.compare(a.x, b.x));
		}
		for (ArrayList<Post> col : cols.values()) {
			col.sort((a, b) -> Long.compare(a.y, b.y));
		}

		HashMap<Post, ArrayList<Post>> edges = new HashMap<>();
		for (Post post : posts) {
			edges.put(post, new ArrayList<>());
		}
		for (ArrayList<Post> row : rows.values()) {
			for (int i = 0; i < row.size(); i += 2) {
				Post a = row.get(i);
				Post b = row.get(i + 1);

				edges.get(a).add(b);
				edges.get(b).add(a);
			}
		}
		for (ArrayList<Post> col : cols.values()) {
			for (int i = 0; i < col.size(); i += 2) {
				Post a = col.get(i);
				Post b = col.get(i + 1);

				edges.get(a).add(b);
				edges.get(b).add(a);
			}
		}

		ArrayList<Post> orderedPosts = new ArrayList<>();
		HashSet<Post> seen = new HashSet<>();

		Post cur = posts[0];
		seen.add(cur);
		orderedPosts.add(cur);
		while (seen.size() < p) {
			for (Post adjacent : edges.get(cur)) {
				if (!seen.contains(adjacent)) {
					orderedPosts.add(adjacent);
					seen.add(adjacent);
					cur = adjacent;
					break;
				}
			}
		}

		first = orderedPosts.get(0);
		last = orderedPosts.get(orderedPosts.size() - 1);

		for (int i = 0; i < orderedPosts.size(); i++) {
			perimeter += orderedPosts.get(i).dist(orderedPosts.get((i + 1) % orderedPosts.size()));
			orderedPosts.get(i).n = i;
		}
		ArrayList<Long> postPrefixSum = new ArrayList<>();
		postPrefixSum.add(0L);
		for (int i = 1; i < orderedPosts.size(); i++) {
			postPrefixSum.add(postPrefixSum.get(i - 1) + orderedPosts.get(i).dist(orderedPosts.get(i - 1)));
		}
		HashMap<Post, Long> postPrefixSumMap = new HashMap<>();
		for (int i = 0; i < orderedPosts.size(); i++) {
			postPrefixSumMap.put(orderedPosts.get(i), postPrefixSum.get(i));
		}

		long[] diffArr = new long[p];

		for (int i = 0; i < n; i++) {
			long x1 = in.nextLong();
			long y1 = in.nextLong();
			long x2 = in.nextLong();
			long y2 = in.nextLong();

			long dist1 = getDist(rows, cols, x1, y1, postPrefixSumMap);
			long dist2 = getDist(rows, cols, x2, y2, postPrefixSumMap);
			long fPathDist = strictMod(dist2 - dist1, perimeter);
			long bPathDist = strictMod(dist1 - dist2, perimeter);

			if (fPathDist > bPathDist) {
				{
					long temp = x1;
					x1 = x2;
					x2 = temp;
				}
				{
					long temp = y1;
					y1 = y2;
					y2 = temp;

				}
			}

			if (x1 == x2 && y1 == y2) {
				if (getPost(rows, cols, x1, y1).length == 1) {
					int index = getPost(rows, cols, x1, y1)[0].n;
					diffArr[index]++;
					if (index + 1 < diffArr.length)
						diffArr[index + 1]--;
				}

				continue;
			}

			Post[] range1 = getPost(rows, cols, x1, y1);
			Post[] range2 = getPost(rows, cols, x2, y2);

			if (Arrays.deepEquals(range1, range2)) {
				continue;
			}

			Post post1;
			Post post2;

			if (range1.length == 1) {
				post1 = range1[0];
			} else {
				long d1 = getDist(rows, cols, x1, y1, postPrefixSumMap);
				if (modDistF(postPrefixSumMap.get(range1[0]), d1, perimeter) > modDistF(postPrefixSumMap.get(range1[1]),
						d1, perimeter)) {
					post1 = range1[0];
				} else {
					post1 = range1[1];
				}
			}

			if (range2.length == 1) {
				post2 = range2[0];
			} else {
				long d2 = getDist(rows, cols, x2, y2, postPrefixSumMap);
				if (modDistF(postPrefixSumMap.get(range2[0]), d2, perimeter) < modDistF(postPrefixSumMap.get(range2[1]),
						d2, perimeter)) {
					post2 = range2[0];
				} else {
					post2 = range2[1];
				}
			}
			if (postPrefixSumMap.get(post1) > postPrefixSumMap.get(post2)) {
				diffArr[0]++;

				if (post2.n + 1 < diffArr.length)
					diffArr[post2.n + 1]--;

				diffArr[post1.n]++;
			} else {
				diffArr[post1.n]++;

				if (post2.n + 1 < diffArr.length)
					diffArr[post2.n + 1]--;
			}
		}

		long sum = 0;
		for (int i = 0; i < p; i++) {
			sum += diffArr[i];
			orderedPosts.get(i).c = sum;
		}

		for (Post post : arr) {
			System.out.println(post.c);
		}

	}

	static long modDistF(long a, long b, long m) {
		return strictMod(b - a, m);
	}

	static Post min(Post[] arr, HashMap<Post, Long> distanceMap) {
		Post min = arr[0];

		for (int i = 0; i < arr.length; i++) {
			if (distanceMap.get(arr[i]) < distanceMap.get(min)) {
				min = arr[i];
			}
		}

		return min;
	}

	static Post max(Post[] arr, HashMap<Post, Long> distanceMap) {
		Post max = arr[0];

		for (int i = 0; i < arr.length; i++) {
			if (distanceMap.get(arr[i]) > distanceMap.get(max)) {
				max = arr[i];
			}
		}

		return max;
	}

	static long strictMod(long x, long m) {
		long val = x % m;

		if (val < 0)
			val += m;
		return val;
	}

	static long getDist(HashMap<Long, ArrayList<Post>> rows, HashMap<Long, ArrayList<Post>> cols, long x, long y,
			HashMap<Post, Long> postPrefixSumMap) {

		Post[] posts = getPost(rows, cols, x, y);

		if (posts.length == 1)
			return postPrefixSumMap.get(posts[0]);

		if (posts[0] == first && posts[1] == last) {
			return last.dist(new Post(x, y, -1)) + postPrefixSumMap.get(last);
		}

		if (postPrefixSumMap.get(posts[0]) > postPrefixSumMap.get(posts[1])) {
			return posts[1].dist(new Post(x, y, -1)) + postPrefixSumMap.get(posts[1]);
		}
		if (postPrefixSumMap.get(posts[1]) > postPrefixSumMap.get(posts[0])) {
			long dist = posts[0].dist(new Post(x, y, -1));
			return postPrefixSumMap.get(posts[0]) + dist;
		}

		return -1;
	}
	/*
	 * 1 12 0 0 2 0 2 1 1 1 1 2 3 2 3 3 1 3 1 4 2 4 2 5 0 5 0 2 2 2
	 */

	static Post[] getPost(HashMap<Long, ArrayList<Post>> rows, HashMap<Long, ArrayList<Post>> cols, long x, long y) {
		ArrayList<Post> candidateRow = null;
		ArrayList<Post> candidateCol = null;
		if (rows.containsKey(y)) {
			candidateRow = rows.get(y);
		}
		if (cols.containsKey(x)) {
			candidateCol = cols.get(x);
		}

		if (candidateRow != null) {
			int index = Collections.binarySearch(candidateRow, new Post(x, 0, -1), (a, b) -> Long.compare(a.x, b.x));

			if (index >= 0) {
				return new Post[] { candidateRow.get(index) };
			}

			index = -index - 1;

			if (index % 2 == 1)
				return new Post[] { candidateRow.get(index - 1), candidateRow.get(index) };
		}
		if (candidateCol != null) {
			int index = Collections.binarySearch(candidateCol, new Post(0, y, -1), (a, b) -> Long.compare(a.y, b.y));
			if (index >= 0) {
				return new Post[] { candidateCol.get(index) };
			}

			index = -index - 1;
			if (index % 2 == 1)
				return new Post[] { candidateCol.get(index - 1), candidateCol.get(index) };
		}

		return null;
	}

	static class Post {
		long x, y;
		int n;
		long c = 0;

		public Post(long x, long y, int n) {
			super();
			this.x = x;
			this.y = y;
			this.n = n;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
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
			Post other = (Post) obj;
			return x == other.x && y == other.y;
		}

		public long dist(Post other) {
			if (x == other.x) {
				return Math.abs(y - other.y);
			} else {
				return Math.abs(x - other.x);
			}
		}
	}

}
