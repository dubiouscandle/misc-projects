package silver_december2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class ConnectingTwoBarns {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int testCases = s.nextInt();

		for (int i = 0; i < testCases; i++) {
			handle(s);
		}

	}

	private static void handle(Scanner s) {
		int numFields = s.nextInt();
		int numPaths = s.nextInt();

		boolean[][] adjMatrix = new boolean[numFields][numFields];

		for (int i = 0; i < numPaths; i++) {
			int farm1 = s.nextInt() - 1;
			int farm2 = s.nextInt() - 1;

			adjMatrix[farm1][farm2] = true;
			adjMatrix[farm2][farm1] = true;
		}

		int[] componentNumbers = new int[numFields];
		Arrays.fill(componentNumbers, -1);

		int id = 0;
		for (int i = 0; i < numFields; i++) {
			if (componentNumbers[i] == -1) {
				fill(adjMatrix, componentNumbers, i, id);
				id++;
			}
		}

		int numComponents = id;

		List<Integer>[] componentFarmNumbers = new ArrayList[numComponents];

		for (int i = 0; i < numComponents; i++) {
			componentFarmNumbers[i] = new ArrayList<>();
		}

		for (int i = 0; i < numFields; i++) {
			componentFarmNumbers[componentNumbers[i]].add(i);
		}

		int minDiffSquared = Integer.MAX_VALUE;

		for (int i = 1; i < numComponents - 1; i++) {
			int minDiff1 = findMinDifferenceSquared(componentFarmNumbers[0], componentFarmNumbers[i]);
			int minDiff2 = findMinDifferenceSquared(componentFarmNumbers[numComponents - 1], componentFarmNumbers[i]);

			if (minDiff1 + minDiff2 < minDiffSquared) {
				minDiffSquared = minDiff1 + minDiff2;
			}
		}

		int oneBridgeDiff = findMinDifferenceSquared(componentFarmNumbers[0], componentFarmNumbers[numComponents - 1]);

		if(oneBridgeDiff < minDiffSquared)
			minDiffSquared = oneBridgeDiff;
		
		System.out.println(minDiffSquared);

		System.out.println(Arrays.toString(componentFarmNumbers));
		System.out.println(Arrays.toString(componentNumbers));
	}

	private static int findMinDifferenceSquared(List<Integer> listA, List<Integer> listB) {
		int minDiff = Integer.MAX_VALUE;

		for (int i : listA) {
			for (int j : listB) {
				int diff = Math.abs(i - j);
				if (diff < minDiff) {
					minDiff = diff;
				}
			}
		}

		return minDiff * minDiff;
	}

	private static void fill(boolean[][] adjMatrix, int[] componentNumbers, int startingFarm, int id) {
		Stack<Integer> stack = new Stack<>();
		Set<Integer> visited = new HashSet<>();

		stack.add(startingFarm);
		visited.add(startingFarm);

		while (!stack.isEmpty()) {
			int currentFarm = stack.pop();

			componentNumbers[currentFarm] = id;

			for (int i = 0; i < adjMatrix.length; i++) {
				if (adjMatrix[currentFarm][i] && !visited.contains(i)) {
					stack.add(i);
					visited.add(i);
				}
			}

		}

	}
}
