package com.dubiouscandle.connect4solver;

import java.util.Scanner;

public class Solver {
	private long nodeCount = 0;
	static int[] colOrder = new int[] { 3, 4, 2, 5, 1, 6, 0 };
	private TranspositionTable transTable = new TranspositionTable(8388593);

	public int negamax(Position p, int alpha, int beta) {
		nodeCount++;

		if (p.moves() == Position.MAX_STONES) {
			return 0;
		}

		for (int x = 0; x < Position.WIDTH; x++) {
			if (p.canPlay(x) && p.isWinningMove(x)) {
				return (Position.MAX_STONES + 1 - p.moves()) / 2;
			}
		}

		int max = (Position.MAX_STONES - 1 - p.moves()) / 2;
		int val = transTable.get(p.key());

		if (val != 0) {
			max = val + Position.MIN_SCORE - 1;
		}

		if (beta > max) {
			beta = max;
			if (alpha >= beta) {
				return beta;
			}
		}

		for (int x = 0; x < Position.WIDTH; x++) {
			if (p.canPlay(colOrder[x])) {
				Position p2 = p.clone();
				p2.play(colOrder[x]);
				int score = -negamax(p2, -beta, -alpha);
				if (score >= beta) {
					return score;
				}
				if (score > alpha) {
					alpha = score;
				}
			}
		}

		transTable.put(p.key(), alpha - Position.MIN_SCORE + 1);

		return alpha;
	}

	public void reset() {
		nodeCount = 0;
		transTable.reset();
	}

    int solve(const Position &P, bool weak = false) 
    {
      if(weak) 
        return negamax(P, -1, 1);
      else 
        return negamax(P, -Position::WIDTH*Position::HEIGHT/2, Position::WIDTH*Position::HEIGHT/2);
    }

	public static void main(String[] args) {
		Position p = new Position();
		Solver solver = new Solver();

		Scanner in = new Scanner(System.in);

		while (true) {
			int input = in.nextInt();
			if (input == -1) {
				for (int i = 0; i < 7; i++) {
					if (!p.canPlay(i))
						continue;

					Position clone = p.clone();
					clone.play(i);

					solver.reset();
					int score = solver.negamax(clone, Integer.MIN_VALUE, Integer.MAX_VALUE);
					System.out.print(score + " ");
				}
			}

			p.play(input);

			System.out.println(p);
		}
		// 0 0 0 2 2 2 4 4 4 6 6 6 0 1 2 3 4 5 6 -1
	}
}
