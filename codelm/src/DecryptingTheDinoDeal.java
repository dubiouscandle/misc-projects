
public class DecryptingTheDinoDeal {

	public static void main(String[] args) {
		solution("906614343");
	}

	public static void solution(String humanNum) {
		if (humanNum.equals("0")) {
			System.out.println(0);
			return;
		}

		int n = Integer.parseInt(humanNum);
		boolean negative = false;
		
		if(n < 0) {
			n = -n;
			negative = true;
		}
		
		StringBuilder out = new StringBuilder();
		
		while (n > 0) {
			out.append(n % 7);

			n /= 7;
		}
		
		if(negative) {
			out.append('-');
		}
		
		out.reverse();

		System.out.println(out);
	}
}
