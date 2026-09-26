import java.util.Random;
import java.util.Stack;

public class Escape {

	public static void main(String[] args) {
//		String[][] forest = new String[][] { 
//			{ "S", "-", "30", "-" }, 
//			{ "h", "50", "-", "30" },
//			{ "-", "20", "50", "20" }, 
//			{ "40", "-", "-", "E" }, };

		Random random = new Random();
		System.out.print("{");
		for (int i = 0; i < 20; i++) {
			System.out.print("{");
			for (int j = 0; j < 20; j++) {
				System.out.print("\"");
				if (random.nextInt(20) == 0) {
					System.out.print('h');
				} else {
					int n = random.nextInt(0, 20);
					System.out.print(n == 0 ? "-" : n);
				}
				System.out.print("\"");
				if (j != 19)
					System.out.print(", ");
			}
			System.out.println("},");
		}
		System.out.print("}");

		String[][] forest = { { "S", "1", "1", "1", "1", "1", "1", "1", "1", "1", "91", "E" } };

//		String[][] forest = 
//				{{"16", "1", "15", "14", "12", "13", "5", "18", "15", "17", "9", "11", "5", "1", "10", "7", "16", "8", "11", "11"},
//				{"17", "15", "4", "5", "3", "2", "16", "-", "13", "6", "17", "3", "-", "13", "15", "6", "1", "h", "4", "7"},
//				{"3", "E", "2", "-", "17", "6", "4", "4", "19", "19", "19", "13", "6", "11", "7", "17", "1", "10", "18", "4"},
//				{"19", "14", "3", "9", "h", "6", "16", "h", "16", "3", "8", "14", "18", "4", "3", "18", "18", "-", "13", "19"},
//				{"15", "3", "2", "8", "5", "8", "6", "16", "7", "5", "10", "9", "15", "15", "6", "4", "11", "16", "7", "5"},
//				{"h", "3", "15", "h", "9", "19", "16", "11", "1", "13", "6", "7", "14", "-", "9", "17", "h", "15", "13", "1"},
//				{"2", "6", "17", "11", "10", "12", "4", "19", "-", "11", "13", "13", "7", "5", "7", "19", "-", "17", "8", "3"},
//				{"14", "19", "16", "10", "h", "6", "7", "2", "7", "10", "12", "10", "5", "3", "11", "9", "13", "10", "14", "8"},
//				{"10", "19", "1", "2", "8", "4", "9", "19", "-", "13", "17", "3", "2", "11", "6", "13", "18", "18", "15", "2"},
//				{"1", "13", "17", "16", "19", "6", "18", "18", "-", "4", "14", "16", "9", "6", "16", "5", "18", "7", "7", "3"},
//				{"11", "10", "19", "2", "1", "1", "6", "2", "19", "19", "17", "16", "19", "15", "19", "14", "8", "9", "19", "4"},
//				{"3", "6", "3", "19", "16", "17", "5", "18", "7", "2", "5", "13", "9", "15", "18", "-", "19", "3", "16", "h"},
//				{"8", "14", "2", "5", "1", "10", "14", "14", "10", "17", "5", "3", "4", "4", "3", "10", "10", "2", "9", "4"},
//				{"16", "15", "17", "13", "-", "5", "16", "12", "8", "18", "3", "12", "9", "3", "2", "3", "h", "1", "15", "12"},
//				{"8", "-", "9", "16", "18", "19", "16", "15", "9", "16", "h", "9", "8", "17", "-", "6", "10", "3", "5", "3"},
//				{"18", "4", "9", "4", "-", "4", "7", "2", "2", "19", "3", "5", "1", "7", "h", "2", "18", "13", "12", "4"},
//				{"1", "12", "h", "14", "6", "14", "4", "12", "7", "h", "13", "7", "4", "5", "10", "16", "18", "5", "11", "3"},
//				{"14", "18", "15", "14", "9", "3", "9", "3", "-", "5", "18", "1", "4", "5", "13", "7", "1", "16", "19", "18"},
//				{"5", "5", "6", "-", "12", "3", "7", "h", "6", "15", "13", "13", "12", "19", "S", "18", "14", "8", "16", "9"},
//				{"10", "10", "7", "11", "11", "17", "1", "19", "14", "6", "13", "5", "14", "2", "9", "11", "8", "h", "10", "2"},
//		};
		solution(forest);
	}

	public static void solution(String[][] forest) {
		new Solution(forest).print();
	}

	public static class Solution {
		int[][] values;
		Stack<Path> stack = new Stack<>();
		int n, m;
		int endX, endY;

		public Solution(String[][] forest) {
			n = forest.length;
			m = forest[0].length;

			values = new int[n][m];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					String string = forest[i][j];

					if (string.equals("S")) {
						stack.add(new Path(i, j, 100));
					} else if (string.equals("E")) {
						endX = i;
						endY = j;
					} else {
						int value;

						if (string.equals("h")) {
							value = +100;
						} else if (string.equals("-")) {
							value = 0;
						} else {
							value = -Integer.parseInt(string);
						}

						values[i][j] = value;
					}

				}
			}
		}

		class Path implements Cloneable {
			public boolean[][] visited = new boolean[n][m];
			public int x, y, health;

			public Path(int x, int y, int h) {
				super();
				visited[x][y] = true;
				this.x = x;
				this.y = y;
				this.health = h;
			}

			@Override
			public Path clone() {
				Path clone = new Path(x, y, health);

				clone.visited = new boolean[n][m];

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						clone.visited[i][j] = visited[i][j];
					}
				}

				return clone;
			}

			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < n; i++) {
					for (int j = 0; j < m; j++) {
						sb.append(visited[i][j] ? 1 : 0);
					}
					sb.append('\n');
				}
				return sb.toString();
			}
		}

		public void print() {
			int max = -1;

			while (!stack.isEmpty()) {
				System.out.println(stack.size());
				Path cur = stack.pop();
				int x = cur.x;
				int y = cur.y;

				cur.health += values[x][y];
				cur.health = Math.min(100, cur.health);

				if (cur.health <= 0) {
					continue;
				}

				if (x == endX && y == endY) {
					if (cur.health > max) {
						max = cur.health;
						continue;
					}
				}

				if (x + 1 < n && !cur.visited[x + 1][y]) {
					Path clone = cur.clone();
					clone.visited[x + 1][y] = true;
					clone.x++;
					stack.add(clone);
				}
				if (x - 1 >= 0 && !cur.visited[x - 1][y]) {
					Path clone = cur.clone();
					clone.visited[x - 1][y] = true;
					clone.x--;
					stack.add(clone);
				}
				if (y + 1 < m && !cur.visited[x][y + 1]) {
					Path clone = cur.clone();
					clone.visited[x][y + 1] = true;
					clone.y++;
					stack.add(clone);
				}
				if (y - 1 >= 0 && !cur.visited[x][y - 1]) {
					Path clone = cur.clone();
					clone.visited[x][y - 1] = true;
					clone.y--;
					stack.add(clone);
				}
			}

			System.out.println(max);
		}
	}

}