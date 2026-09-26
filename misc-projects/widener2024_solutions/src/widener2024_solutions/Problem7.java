/* Problem 7
 * 
 * Given an unsorted array of distinct integers, your task is to calculate the sum of the absolute values of the distances 
 * for each element in the array based on its index in the sorted array.
 * 
 * Input:
 * 1. A positive integer n - size of the array
 * 2. An unsorted array of n distinct integers
 * 
 * Output: The sum of the absolute values of the distances for each element from their index in the sorted array.
 * 
 * */

package widener2024_solutions;

import java.util.Arrays;
import java.util.Scanner;

public class Problem7 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		Wrapper[] arr = new Wrapper[s.nextInt()];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Wrapper(s.nextInt(), i);
		}
		
		Arrays.sort(arr);
		
		int sum = 0;
		
		for(int i = 0; i < arr.length; i++) {
			sum += Math.abs(i - arr[i].originalIndex);
		}
		
		System.out.println(sum);

	}

	public static class Wrapper implements Comparable<Wrapper>{
		int value, originalIndex;

		public Wrapper(int value, int originalIndex) {
			this.value = value;
			this.originalIndex = originalIndex;
		}

		@Override
		public int compareTo(Wrapper o) {
			return Integer.compare(value, o.value);
		}
	}
}
