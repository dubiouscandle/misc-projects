package com.dubiouscandle.vcg;

/**
 * Interface representing a Vectorizable Combinatorial Game (VCG). <br>
 * It is recommended that all implementations implement the toString method for
 * easier debugging
 */
public interface VCG {
	/**
	 * An enum representing a move result.
	 */
	public static enum MoveResult {
		DRAW, PLAYER_ONE_WINS, PLAYER_TWO_WINS, VALID_MOVE, INVALID_MOVE;
	}

	/**
	 * An enum representing a player
	 */
	public static enum Player {
		ONE, TWO;

		/**
		 * Returns the other player from this one
		 * 
		 * @return the other player from this one
		 */
		public Player other() {
			return this == ONE ? TWO : ONE;
		}

		/**
		 * Returns {@link MoveResult#PLAYER_ONE_WINS} if this equals ONE.<br>
		 * Returns {@link MoveResult#PLAYER_TWO_WINS} if this equals TWO.<br>
		 * 
		 * @return {@link MoveResult#PLAYER_ONE_WINS} if this equals ONE,
		 *         {@link MoveResult#PLAYER_TWO_WINS} if this equals TWO
		 */
		public MoveResult toMoveResult() {
			return this == ONE ? MoveResult.PLAYER_ONE_WINS : MoveResult.PLAYER_TWO_WINS;
		}

		/**
		 * returns true if this equals ONE
		 * 
		 * @return true if this equals ONE
		 */
		public boolean isOne() {
			return this == ONE;
		}

		/**
		 * returns true if this equals TWO
		 * 
		 * @return true if this equals TWO
		 */
		public boolean isTwo() {
			return this == TWO;
		}

		/**
		 * returns 1 if this equals ONE, otherwise 2 if this equals TWO
		 * 
		 * @return 1 if this equals ONE, otherwise 2 if this equals TWO
		 */
		public int toInt() {
			return this == ONE ? 1 : 2;
		}
	}

	/**
	 * Processes a player's move in the game.
	 * 
	 * This method takes an integer representing the move the player is attempting
	 * to make, processes it, and returns the result of that move. The result should
	 * indicate whether the move was valid, invalid, or if the game ended with a win
	 * or draw.
	 *
	 * @param move The move that the player is attempting to make. The exact nature
	 *             of this parameter will depend on how the game defines a move
	 *             (e.g., a position on a board, a specific action, etc.).
	 * @return An integer representing the outcome of the move. The possible
	 *         outcomes should be:<br>
	 *         - {@link MoveResult#DRAW}: the game has ended in a draw.<br>
	 *         - {@link MoveResult#PLAYER_ONE_WINS}: Player One wins the game.<br>
	 *         - {@link MoveResult#PLAYER_TWO_WINS}: Player Two wins the game.<br>
	 *         - {@link MoveResult#VALID_MOVE}: the move is valid and has been
	 *         accepted.<br>
	 *         - {@link MoveResult#INVALID_MOVE}: the move is not allowed or not
	 *         possible. If this value is returned, the current state of the board
	 *         should not be affected.<br>
	 */
	MoveResult play(int move);

	/**
	 * This method should return whether the specified move is valid. This method
	 * should be consistent with {@link #play(int)} in that if this method returns
	 * true for some move, then playing that move using {@link #play(int)} should
	 * not return {@link MoveResult#INVALID_MOVE}
	 * 
	 * @param move
	 * @return true if the move is valid, false if the move is invalid.
	 */
	boolean isValidMove(int move);

	/**
	 * Converts the current state of the game for the specified player into a vector
	 * representation.
	 * 
	 * This method is used to transform the game state into a numerical format that
	 * can be used for analysis, machine learning, or other purposes. The vector
	 * representation allows for an easier comparison of different game states or
	 * for feeding the game state into algorithms that require vectorized input.
	 * 
	 * @param player The player whose game state is to be converted to a vector. It
	 *               is expected that the value of `player' is either
	 *               {@link Player#ONE} or {@link Player#TWO}.
	 * @param out    An array of floats where the resulting vector will be stored.
	 *               This array will be populated with the game state information in
	 *               a format that is suitable for further processing. The required
	 *               length of this array will depend on how the game defines it.
	 */
	void toVector(Player player, float[] out);

	/**
	 * Returns the player to move. The outputs of this method should be either
	 * {@link Player#ONE} or {@link Player#TWO} depending on if player one or player
	 * two is moving
	 * 
	 * @return the player to move
	 */
	Player currentPlayer();

	/**
	 * Returns the required size of the vector in {@link #toVector(Player, float[])}
	 * 
	 * @return the required size of the vector in {@link #toVector(Player, float[])}
	 */
	int vectorSize();
}
