package archive;

import java.util.Scanner;
import java.util.Stack;

public class CowFrisbee {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		int[] arr = new int[n];

		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt() - 1;
		}

		long c = count(arr);
		for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
		c += count(arr);

		System.out.println(c);
	}

	private static long count(int[] arr) {
		int[] reverseArr = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			reverseArr[arr[i]] = i;
		}

		long c = 0;

		Stack<Integer> stack = new Stack<>();
		stack.add(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			int cur = arr[i];

			while (!stack.isEmpty() && cur > stack.peek()) {
				stack.pop();
			}

			if (!stack.isEmpty()) {
				c += i - reverseArr[stack.peek()] + 1;
			}
			stack.push(cur);
		}

		return c;
	}

}
