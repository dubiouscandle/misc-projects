//Name: Gavin Liu
//Date: Nov 23, 2024
//Title: TicTacToe3D.java
//Description: the tic tac toe class object thingy majigy it has the interface structure thing idk

package game;

public class TicTacToe3D {
	// constances for x o and empty
	public static final byte EMPTY = 0;
	public static final byte X = 1;
	public static final byte O = 2;

	private byte turn;

	// usue your brain for thse fields
	private int boardSize;
	private int connectN;
	private byte winner;
	private boolean isGameOver;

	public byte getWinner() {
		return winner;
	}

	// it resets the board
	public void reset() {
		turn = X;
		winner = EMPTY;
		isGameOver = false;
		numEmptySpaces = boardSize * boardSize * boardSize;
		position = new byte[boardSize][boardSize][boardSize];
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	int numEmptySpaces;
	private byte[][][] position;

	public TicTacToe3D() {
		this(4, 4);
	}

	public TicTacToe3D(int boardSize, int connectN) {
		this.boardSize = boardSize;
		this.connectN = connectN;

		reset();
	}

	public void play(int x, int y, int z) {
		if (position[x][y][z] != EMPTY)
			throw new IllegalArgumentException("not valid move");

		if (isGameOver)
			throw new IllegalArgumentException("game is over");

		position[x][y][z] = turn;
		numEmptySpaces--;

		// use your brain again
		if (checkWin(x, y, z)) {
			isGameOver = true;
			winner = turn;
		}
		// idk use your brain for this one
		else if (numEmptySpaces <= 0) {
			isGameOver = true;
			winner = EMPTY;
		}

		// use your brain
		if (turn == X)
			turn = O;
		else
			turn = X;

	}

	// this does the same thingy as the connect 4 code except with a biajillion more
	// cases cuz diags and stuff and more dimensions
	private boolean checkWin(int x, int y, int z) {
		// the axis runs
		int xCounter = 0;
		int yCounter = 0;
		int zCounter = 0;

		// planar runs
		int xyCounter1 = 0;
		int xyCounter2 = 0;

		int yzCounter1 = 0;
		int yzCounter2 = 0;

		int zxCounter1 = 0;
		int zxCounter2 = 0;

		// the 4 diagonal runs
		int diagCounter1 = 0;
		int diagCounter2 = 0;
		int diagCounter3 = 0;
		int diagCounter4 = 0;

		for (int i = -connectN + 1; i < connectN; i++) {
			// the new coords kinda
			int xi = x + i;
			int yi = y + i;
			int zi = z + i;

			// the new cords but going the opposite way for the other diags and stuff
			int yni = y - i;
			int zni = z - i;
			if (isValid(xi, y, z) && position[xi][y][z] == turn)
				xCounter++;
			if (isValid(x, yi, z) && position[x][yi][z] == turn)
				yCounter++;
			if (isValid(x, y, zi) && position[x][y][zi] == turn)
				zCounter++;

			if (isValid(xi, yi, z) && position[xi][yi][z] == turn)
				xyCounter1++;
			if (isValid(xi, yni, z) && position[xi][yni][z] == turn)
				xyCounter2++;

			if (isValid(x, yi, zi) && position[x][yi][zi] == turn)
				yzCounter1++;
			if (isValid(x, yni, zi) && position[x][yni][zi] == turn)
				yzCounter2++;

			if (isValid(xi, y, zi) && position[xi][y][zi] == turn)
				zxCounter1++;
			if (isValid(xi, y, zni) && position[xi][y][zni] == turn)
				zxCounter2++;

			if (isValid(xi, yi, zi) && position[xi][yi][zi] == turn)
				diagCounter1++;

			if (isValid(xi, yni, zi) && position[xi][yni][zi] == turn)
				diagCounter2++;

			if (isValid(xi, yni, zni) && position[xi][yni][zni] == turn)
				diagCounter3++;

			if (isValid(xi, yi, zni) && position[xi][yi][zni] == turn)
				diagCounter4++;
		}

		// yes
		return xCounter >= connectN || yCounter >= connectN || zCounter >= connectN || xyCounter1 >= connectN
				|| xyCounter2 >= connectN || yzCounter1 >= connectN || yzCounter2 >= connectN || zxCounter1 >= connectN
				|| zxCounter2 >= connectN || diagCounter1 >= connectN || diagCounter2 >= connectN
				|| diagCounter3 >= connectN || diagCounter4 >= connectN;
	}

	// checks if the x,y,z is in bounds of hte cube
	private boolean isValid(int x, int y, int z) {
		return x >= 0 && x < boardSize && y >= 0 && y < boardSize && z >= 0 && z < boardSize;
	}

	public byte[][][] getPosition() {
		return position;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public byte getTurn() {
		return turn;
	}

}
