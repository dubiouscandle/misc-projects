package com.dubiouscandle.vcg;

public class TicTacToe implements VCG {
	private static final int[] WIN_MASKS = { 0b111000000, 0b000111000, 0b000000111, 0b100100100, 0b010010010,
			0b001001001, 0b100010001, 0b001010100, };
	private static final int FULL_MASK = 511;

	private Player player = Player.ONE;
	private int bits = 0;

	public TicTacToe() {
	}

	@Override
	public MoveResult play(int move) {
		if (!isValidMove(move)) {
			return MoveResult.INVALID_MOVE;
		}

		bits |= 1 << (move + (player == Player.ONE ? 0 : 9));

		if (checkWin(player)) {
			return player.toMoveResult();
		} else if (((bits & FULL_MASK) | (bits >> 9)) == FULL_MASK) {
			return MoveResult.DRAW;
		} else {
			return MoveResult.VALID_MOVE;
		}
	}

	private boolean checkWin(Player player) {
		int bitsCopy = bits;

		bitsCopy >>= player == Player.ONE ? 0 : 9;

		for (int mask : WIN_MASKS) {
			if ((bitsCopy & mask) == mask) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Fills the specified array in the following format: <br>
	 * inclusive - exclusive<br>
	 * 0 - 9: pieces of self<br>
	 * 9 - 18: pieces of other<br>
	 * 18 - 27: legal moves (0 = illegal, 1 = legal)<br>
	 */
	@Override
	public void toVector(Player player, float[] out) {
		for (int i = 0; i < 9; i++) {
			out[i] = (bits >> (i + (player == Player.ONE ? 0 : 9))) & 1;
		}
		for (int i = 0; i < 9; i++) {
			out[i + 9] = (bits >> (i + (player == Player.ONE ? 9 : 0))) & 1;
		}
		for (int i = 0; i < 9; i++) {
			out[i + 18] = isValidMove(i) ? 1 : 0;
		}
	}

	@Override
	public boolean isValidMove(int move) {
		return 0 <= move && move < 9 && ((bits >> move) & 1) == 0 && ((bits >> (move + 9)) & 1) == 0;
	}

	@Override
	public Player currentPlayer() {
		return player;
	}

	@Override
	public int vectorSize() {
		return 27;
	}
}
