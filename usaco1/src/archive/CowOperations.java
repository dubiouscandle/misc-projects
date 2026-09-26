package archive;
import java.util.Arrays;
import java.util.Scanner;

public class CowOperations {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		String s = in.next();
		int len = s.length();
		int q = in.nextInt();
		int[] substrL = new int[q];
		int[] substrR = new int[q];

		for (int i = 0; i < q; i++) {
			substrL[i] = in.nextInt() - 1;
			substrR[i] = in.nextInt() - 1;
		}

		int[] cPrefixSum = new int[len];
		int[] oPrefixSum = new int[len];
		int[] wPrefixSum = new int[len];

		int cSum = 0;
		int oSum = 0;
		int wSum = 0;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);

			if (c == 'C')
				cSum++;
			else if (c == 'O')
				oSum++;
			else
				wSum++;

			cPrefixSum[i] = cSum;
			oPrefixSum[i] = oSum;
			wPrefixSum[i] = wSum;
		}

		for (int i = 0; i < q; i++) {
			int l = substrL[i];
			int r = substrR[i];

			int c = cPrefixSum[r] - ((l == 0) ? 0 : cPrefixSum[l - 1]);
			int o = oPrefixSum[r] - ((l == 0) ? 0 : oPrefixSum[l - 1]);
			int w = wPrefixSum[r] - ((l == 0) ? 0 : wPrefixSum[l - 1]);

			if ((o + w) % 2 == 0 && (c + o) % 2 == 1) {
				System.out.print('Y');
			} else
				System.out.print("N");
		}
	}

}
