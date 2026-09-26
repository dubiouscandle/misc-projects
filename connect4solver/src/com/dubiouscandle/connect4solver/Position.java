package com.dubiouscandle.connect4solver;

public class Position implements Cloneable {
	public static final int WIDTH = 7, HEIGHT = 6;
	public static final int MIN_SCORE = -(WIDTH * HEIGHT) / 2 + 3;
	public static final int MAX_SCORE = (WIDTH * HEIGHT + 1) / 2 - 3;
	public static final int MAX_STONES = WIDTH * HEIGHT;
	
    private static final long BOTTOM_MASK = bottom(WIDTH, HEIGHT);
    private static final long BOARD_MASK = BOTTOM_MASK * ((1L << HEIGHT)-1);

	private long stones;
	private long mask;
	private int numMoves;

	public Position() {
		this(0, 0L, 0L);
	}

	private static long bottom(int width, int height) {
		return width == 0 ? 0 : bottom(width - 1, height) | 1L << (width - 1) * (height + 1);
	}

	private Position(int moves, long bitmap, long mask) {
		this.numMoves = moves;
		this.stones = bitmap;
		this.mask = mask;
	}

	public void play(int col) {
		stones ^= mask;
		mask |= mask + bottomMaskCol(col);
		numMoves++;
	}

	public boolean canWinNext() {
		return winningPosition() & possible();
	}

	public int numMoves() {
		return numMoves;
	}

	public long key() {
		return stones + mask;
	}

	public long possibleNonLosingMoves() {
		long possibleMask = possible();
		long opponentWin = opponentWinningPosition();
		long forcedMoves = possibleMask & opponentWin;

		if (forcedMoves != 0) {
			if ((forcedMoves & (forcedMoves - 1)) != 0) {
				return 0;
			} else {
				possibleMask = forcedMoves;
			}
		}

		return possibleMask & ~(opponentWin >> 1);
	}

	int moveScore(long move) {
		return popcount(computeWinningPosition(stones | move, mask));
	}

	public boolean canPlay(int col) {
		return (mask & topMaskCol(col)) == 0;
	}

	void playCol(int col) {
		play((mask + bottomMaskCol(col)) & columnMask(col));
	}

	public boolean isWinningMove(int col) {
		return winningPosition() & possible() & columnMask(col);
	}

	long winningPosition() {
		return computeWinningPosition(stones, mask);
	}

	public long opponentWinningPosition() {
		return computeWinningPosition(stones ^ mask, mask);
	}

	long possible() {
		return (mask + BOTTOM_MASK) & BOARD_MASK;
	}

	private static boolean alignment(long stones) {
		long m = stones & (stones >> (HEIGHT + 1));
		if ((m & (m >> (2 * (HEIGHT + 1)))) != 0)
			return true;

		m = stones & (stones >> HEIGHT);
		if ((m & (m >> (2 * HEIGHT))) != 0)
			return true;

		m = stones & (stones >> (HEIGHT + 2));
		if ((m & (m >> (2 * (HEIGHT + 2)))) != 0)
			return true;

		m = stones & (stones >> 1);
		if ((m & (m >> 2)) != 0)
			return true;

		return false;
	}

	static int popcount(long m) {
		int c = 0;
		for (c = 0; m != 0; c++)
			m &= m - 1;
		return c;
	}
	
    static long computeWinningPosition(long position, long mask) {
        // vertical;
    	long r = (position << 1) & (position << 2) & (position << 3);

        //horizontal
    	long p = (position << (HEIGHT+1)) & (position << 2*(HEIGHT+1));
        r |= p & (position << 3*(HEIGHT+1));
        r |= p & (position >> (HEIGHT+1));
        p = (position >> (HEIGHT+1)) & (position >> 2*(HEIGHT+1));
        r |= p & (position << (HEIGHT+1));
        r |= p & (position >> 3*(HEIGHT+1));

        //diagonal 1
        p = (position << HEIGHT) & (position << 2*HEIGHT);
        r |= p & (position << 3*HEIGHT);
        r |= p & (position >> HEIGHT);
        p = (position >> HEIGHT) & (position >> 2*HEIGHT);
        r |= p & (position << HEIGHT);
        r |= p & (position >> 3*HEIGHT);

        //diagonal 2
        p = (position << (HEIGHT+2)) & (position << 2*(HEIGHT+2));
        r |= p & (position << 3*(HEIGHT+2));
        r |= p & (position >> (HEIGHT+2));
        p = (position >> (HEIGHT+2)) & (position >> 2*(HEIGHT+2));
        r |= p & (position << (HEIGHT+2));
        r |= p & (position >> 3*(HEIGHT+2));

        return r & (BOARD_MASK ^ mask);
      }


	private static long topMaskCol(int col) {
		return (1L << (HEIGHT - 1)) << (col * (HEIGHT + 1));
	}

	private static long bottomMaskCol(int col) {
		return 1L << (col * (HEIGHT + 1));
	}

	static long colMask(int col) {
		return ((1L << HEIGHT) - 1) << (col * (HEIGHT + 1));
	}

	@Override
	public Position clone() {
		return new Position(numMoves, stones, mask);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int y = HEIGHT - 1; y >= 0; y--) {
			for (int x = 0; x < WIDTH; x++) {
				long xyMask = 1L << (x * (HEIGHT + 1) + y);

				if ((mask & xyMask) == 0) {
					sb.append('.');
					continue;
				}

				if (((stones & xyMask) != 0) ^ (numMoves % 2 != 0)) {
					sb.append('X');
				} else {
					sb.append('O');
				}
			}

			sb.append('\n');
		}

		return sb.toString();
	}

}
