package archive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class CycleCorrespondence {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int k = in.nextInt();

		int[] arrA = new int[k];
		int[] arrB = new int[k];

		boolean[] inCycleA = new boolean[n];
		boolean[] inCycleB = new boolean[n];

		for (int i = 0; i < k; i++) {
			arrA[i] = in.nextInt() - 1;
			inCycleA[arrA[i]] = true;
		}
		for (int i = 0; i < k; i++) {
			arrB[i] = in.nextInt() - 1;
			inCycleB[arrB[i]] = true;
		}

		in.close();

		int c = 0;
		for (int i = 0; i < n; i++) {
			if (!inCycleA[i] && !inCycleB[i]) {
				c++;
			}
		}

		int order1Max = getMaxMatching(arrA, arrB, k);
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 0; i < arrA.length; i++) {
			temp.add(arrA[i]);
		}
		Collections.reverse(temp);
		for (int i = 0; i < arrA.length; i++) {
			arrA[i] = temp.get(i);
		}
		int order2Max = getMaxMatching(arrA, arrB, k);

		System.out.println(c + Math.max(order1Max, order2Max));
	}

	public static int getMaxMatching(int[] arrA, int[] arrB, int k) {
		HashMap<Integer, Integer> arrBIndices = new HashMap<>();

		for (int i = 0; i < k; i++) {
			arrBIndices.put(arrB[i], i);
		}

		int[] offsetsCount = new int[k];
		for (int i = 0; i < k; i++) {
			if (!arrBIndices.containsKey(arrA[i]))
				continue;

			int indexA = i;
			int indexB = arrBIndices.get(arrA[i]);
			int distForward = indexB - indexA;
			if (distForward < 0)
				distForward += k;

			offsetsCount[distForward]++;
		}

		return Arrays.stream(offsetsCount).max().getAsInt();
	}

}
