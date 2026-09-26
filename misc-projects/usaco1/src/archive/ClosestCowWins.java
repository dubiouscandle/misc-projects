package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ClosestCowWins {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int k = in.nextInt();
		int m = in.nextInt();
		int n = in.nextInt();

		Patch[] patches = new Patch[k];

		for (int i = 0; i < k; i++) {
			patches[i] = new Patch(in.nextInt(), in.nextInt());
		}

		Arrays.sort(patches);

		int[] blockers = new int[m + 2];
		blockers[0] = Integer.MIN_VALUE;
		blockers[m + 1] = Integer.MAX_VALUE;
		for (int i = 1; i < m + 1; i++) {
			blockers[i] = in.nextInt();
		}

		Arrays.sort(blockers);
		ArrayList<Long> intervalSums = new ArrayList<>();
		{
			int index = 0;
			for (int i = 0; i < blockers.length - 1; i++) {
				int end = blockers[i + 1];

				long sum = 0;

				while (index < patches.length && patches[index].x < end) {
					sum += patches[index].t;
					index++;
				}

				intervalSums.add(sum);
			}
		}
		ArrayList<Long> maxWithOne = new ArrayList<>();

		{
			int index = 0;
			for (int i = 1; i < blockers.length - 2; i++) {
				int start = blockers[i];
				int end = blockers[i + 1];
				int diff = end - start;
				int range = diff % 2 == 0 ? diff / 2 - 1 : diff / 2;

				long sum = 0;
				Queue<Patch> queue = new LinkedList<>();

				while (index < patches.length && patches[index].x < end) {
					queue.add(patches[index]);
					sum += patches[index].t;

					while (queue.peek().x < patches[index].x - range) {
						sum -= queue.poll().t;
					}

					index++;
				}

				maxWithOne.add(sum);
			}
		}

		ArrayList<Long> contributions = new ArrayList<>();
		contributions.add(intervalSums.get(0));
		contributions.add(intervalSums.get(intervalSums.size() - 1));

		intervalSums.remove(0);
		intervalSums.remove(intervalSums.size() - 1);

		for (int i = 0; i < intervalSums.size(); i++) {
			contributions.add(intervalSums.get(i) - maxWithOne.get(i));
			contributions.add(maxWithOne.get(i));
		}

		Collections.sort(contributions);

		long sum = 0;

		for (int i = contributions.size() - 1; i > contributions.size() - 1 - n; i--) {
			sum += contributions.get(i);
		}

		System.out.println(sum);

	}

	public static class Patch implements Comparable<Patch> {
		public int x, t;

		public Patch(int x, int t) {
			super();
			this.x = x;
			this.t = t;
		}

		@Override
		public int compareTo(ClosestCowWins.Patch o) {
			return Integer.compare(x, o.x);
		}

		@Override
		public String toString() {
			return "Patch [x=" + x + ", t=" + t + "]";
		}
	}
}
