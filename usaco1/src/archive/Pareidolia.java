package archive;
import java.util.Arrays;
import java.util.Scanner;

public class Pareidolia {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		char[] arr = in.next().toUpperCase().toCharArray();
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			if (arr[i] != 'B' && arr[i] != 'E' && arr[i] != 'S' && arr[i] != 'I') {
				arr[i] = 'X';
			}
		}

		char[] bessie = "BESSIE".toCharArray();
		int consumed = 0;
		int begin = 0;
		long sum = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] == bessie[consumed]) {
				if (consumed == 0) {
					begin = i;
				}
				consumed++;

				if (consumed == 6) {
					consumed = 0;
					int end = i;

					int r = n - 1 - end;
					sum += (begin + 1) * (r + 1);
				}
			}
		}

		System.out.println(sum);
	}

}
