package archive;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class MazeTicTacToe {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();

		Tile[][] grid = new Tile[n + 2][n + 2];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = new Tile("###");
			}
		}
		for (int i = 1; i <= n; i++) {
			String next = in.next();
			for (int j = 1; j <= n; j++) {
				grid[i][j] = new Tile(next.substring(j * 3, j * 3 + 3));
			}
		}

		HashMap<Tile, ArrayList<Tile>> edges = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j].type == '#')
					continue;

			}
		}

		HashSet<State> seen = new HashSet<>();
	}

	static class State {
		final Tile cur;
		final char[][] board = new char[3][3];

		State(Tile cur) {
			this.cur = cur;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.deepHashCode(board);
			result = prime * result + Objects.hash(cur);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			return Arrays.deepEquals(board, other.board) && Objects.equals(cur, other.cur);
		}
	}

	static class Tile {
		int x = -1, y = -1;
		char type;// M O # B .

		public Tile(String s) {
			super();
			type = s.charAt(0);

			if (type == 'M' || type == 'O') {
				x = s.charAt(1) - '1';
				y = s.charAt(2) - '1';
			}
		}

	}

}
