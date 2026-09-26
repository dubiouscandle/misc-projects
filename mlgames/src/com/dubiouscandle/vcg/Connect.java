package com.dubiouscandle.vcg;

public class Connect implements VCG {
	private final int n;

	private final int width, height;
	private final int[][] board;
	private final int[] heights;
	private int moveCount = 0;
	private Player player = Player.ONE;

	public Connect(int width, int height, int n) {
		this.n = n;

		board = new int[width][height];
		heights = new int[width];
		this.width = width;
		this.height = height;
	}

	@Override
	public MoveResult play(int move) {
		if (!isValidMove(move)) {
			return MoveResult.INVALID_MOVE;
		}

		int x = move;
		int y = heights[move];

		board[x][y] = player.toInt();
		heights[x]++;
		moveCount++;

		if (checkWin(player, x, y)) {
			return player.toMoveResult();
		} else if (moveCount == width * height) {
			return MoveResult.DRAW;
		}

		player = player.other();

		return MoveResult.VALID_MOVE;
	}

	private boolean checkWin(Player player, int x, int y) {
		int toInt = player.toInt();

		// horizontal
		int count = 0;
		for (int i = Math.max(0, x - n + 1); i <= Math.min(width - 1, x + n - 1); i++) {
			count = (board[i][y] == toInt) ? count + 1 : 0;
			if (count >= n)
				return true;
		}

		// vertical
		count = 0;
		for (int j = Math.max(0, y - n + 1); j <= Math.min(height - 1, y + n - 1); j++) {
			count = (board[x][j] == toInt) ? count + 1 : 0;
			if (count >= n)
				return true;
		}

		// diagonal (top-left to bottom-right)
		count = 0;
		for (int i = -n + 1; i < n; i++) {
			int xi = x + i, yi = y + i;
			if (xi >= 0 && xi < width && yi >= 0 && yi < height) {
				count = (board[xi][yi] == toInt) ? count + 1 : 0;
				if (count >= n)
					return true;
			}
		}

		// diagonal (bottom-left to top-right)
		count = 0;
		for (int i = -n + 1; i < n; i++) {
			int xi = x + i, yi = y - i;
			if (xi >= 0 && xi < width && yi >= 0 && yi < height) {
				count = (board[xi][yi] == toInt) ? count + 1 : 0;
				if (count >= n)
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean isValidMove(int move) {
		return 0 <= move && move < width && heights[move] < height;
	}

	/**
	 * Fills the specified array in the following format: <br>
	 * let k = width * height (total squares)<br>
	 * inclusive - exclusive<br>
	 * 0 - k: pieces of self<br>
	 * k - 2k: pieces of other<br>
	 * 2k - 2k + width: legal moves (0 = illegal, 1 = legal)<br>
	 * total length: width * height * 2 + width
	 */
	@Override
	public void toVector(Player player, float[] out) {
		int playerAsInt = player.toInt();
		int playerOtherAsInt = player.other().toInt();
		for (int i = 0, x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				out[i] = board[x][y] == playerAsInt ? 1f : 0f;
				i++;
			}
		}
		for (int i = 0, x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				out[i + width * height] = board[x][y] == playerOtherAsInt ? 1f : 0f;
				i++;
			}
		}
		for (int i = 0; i < width; i++) {
			out[i + 2 * width * height] = isValidMove(i) ? 1f : 0f;
		}
	}

	@Override
	public Player currentPlayer() {
		return player;
	}

	@Override
	public int vectorSize() {
		return width * height * 2 + width;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				int val = board[x][y];
				sb.append(val == 0 ? '.' : val == 1 ? 'X' : 'O').append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

}
