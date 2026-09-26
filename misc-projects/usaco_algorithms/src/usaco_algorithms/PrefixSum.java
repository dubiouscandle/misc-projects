package usaco_algorithms;

public class PrefixSum {
	public int[] prefixSum(int[] arr) {
		int[] prefixSum = new int[arr.length];
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			prefixSum[i] = sum;
		}

		return prefixSum;
	}
}
