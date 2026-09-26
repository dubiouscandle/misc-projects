package usaco_algorithms;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();

		for (int i = 0; i < 1_000_000_000; i++) {
			arrayList.add(i);
		}

		System.out.printf("%1.8f\n", time(() -> {
			ArrayList<Integer> arr = new ArrayList<>();
			for(int i = 0; i < arrayList.size(); i++) {
				arr.add(arrayList.get(i));
			}
		}) / 1_000_000_000.0);
		System.out.printf("%1.8f\n", time(() -> arrayList.clone()) / 1_000_000_000.0);
	}

	public static long time(Runnable action) {
		long t0 = System.nanoTime();

		action.run();

		return System.nanoTime() - t0;
	}
}
