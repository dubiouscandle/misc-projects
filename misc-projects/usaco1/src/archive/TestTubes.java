package archive;
import java.util.Scanner;
import java.util.Stack;

public class TestTubes {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int t = in.nextInt();

		for (int i = 0; i < t; i++) {
			solution(in);
		}
	}

	private static void solution(Scanner in) {
		int n = in.nextInt();
		int parameter = in.nextInt();

		Stack<Integer> tubeA = constructTube(in.next());
		Stack<Integer> tubeB = constructTube(in.next());

		Integer tubeC = null;
		int p = 0;

		while (
				tubeA.size() > 0
				) {
			Integer a = null;
			if (!tubeA.isEmpty())
				a = tubeA.peek();

			Integer b = null;
			if (!tubeB.isEmpty())
				b = tubeB.peek();

			Integer c = tubeC;

//			System.out.println(tubeA);
//			System.out.println(tubeB);
//			System.out.println(c);
//			System.out.println();
			if (c == null) {
				tubeC = tubeA.pop();
				p++;
				continue;
			}

			if (tubeA.size() >= 2) {
				tubeA.pop();
				p++;
				continue;
			}

			if (tubeA.size() == 1) {
				if (a == c) {
					tubeA.pop();
					p++;
					continue;
				}
			}

			if (tubeB.size() > 0) {
				tubeB.pop();
				p++;
			}
		}
		
		System.out.println("E"+p);
	}

	private static Stack<Integer> constructTube(String s) {
		Stack<Integer> tube = new Stack<>();

		for (int i = 0; i < s.length(); i++) {
			int type = s.charAt(i) - '1' + 1;

			if (tube.size() == 0 || tube.peek() != type) {
				tube.push(type);
			}
		}

		return tube;
	}
}
