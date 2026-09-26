package team29;

import java.util.Arrays;
import java.util.Scanner;

public class Team29Problem7 {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int size = s.nextInt();

		s.close();

		Wrap[] unsorted = new Wrap[size];

		for (int i = 0; i < unsorted.length; i++) {
			unsorted[i] = new Wrap(s.nextInt(), i);
		}
		
		Wrap[] sorted = unsorted.clone();
		
		Arrays.sort(sorted);
		
		int sum = 0;
		
		for(int i = 0; i < sorted.length; i++) {
			int newIndex = i;
			
			sum += Math.abs(sorted[i].originalPosition - newIndex);
			
			
		}

		System.out.println(sum);
	}

	public static class Wrap implements Comparable<Wrap>{
		int value;
		int originalPosition;

		public Wrap(int value, int originalPosition) {
			this.value = value;
			this.originalPosition = originalPosition;
		}

		@Override
		public int compareTo(Wrap o) {
			return Integer.compare(this.value, o.value);
		}
	}

}
