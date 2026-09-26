package com.dubiouscandle.experiments;

import java.util.Random;
import java.util.Scanner;

import com.dubiouscandle.experiments.VCG.MoveResult;
import com.dubiouscandle.experiments.VCG.Player;
import com.dubiouscandle.minineat.ActivationFunction;
import com.dubiouscandle.minineat.Agent;
import com.dubiouscandle.minineat.Trainer;

public class Main {
	public static final Random RANDOM = new Random(438);

	public static final int GENERATION_SIZE = 100;
//	public static final int ITERATIONS_GOAL = 1500;
//	public static final float FITNESS_GOAL = 10;

	public static final int IN_COUNT = 28;
	public static final int OUT_COUNT = 9;

	public static void main(String[] args) {
		Trainer trainer = new Trainer(null, ActivationFunction.ReLU, IN_COUNT, OUT_COUNT, GENERATION_SIZE,
				RANDOM.nextLong()) {
			@Override
			protected void assignFitnessValues(Agent[] agents) {
				for (Agent agent : agents) {
					agent.fitness = 0;
				}

				for (int i = 0; i < agents.length; i++) {
					for (int j = i + 1; j < agents.length; j++) {
						TicTacToe ttt = new TicTacToe();

						Agent a1 = agents[i];
						Agent a2 = agents[j];

						if (RANDOM.nextBoolean()) {
							Agent temp = a1;
							a1 = a2;
							a2 = temp;
						}

						while (true) {
							Agent agentToPlay = ttt.currentPlayer() == Player.ONE ? a1 : a2;
							float[] input = new float[ttt.vectorSize() + 1];
							ttt.toVector(ttt.currentPlayer(), input, 1);
							input[0] = 1;

							float[] output = new float[9];
							agentToPlay.propagate(input, output);

							int move = indexOfMax(output);

							if (!ttt.isValidMove(move)) {
								agentToPlay.fitness -= 1f;
								while (!ttt.isValidMove(move)) {
									move = RANDOM.nextInt(0, 9);
								}
							}

							MoveResult result = ttt.play(move);

							if (result == MoveResult.DRAW) {
								break;
							} else if (result == MoveResult.PLAYER_ONE_WINS) {
								a1.fitness += 1;
								a2.fitness -= 1;
								break;
							} else if (result == MoveResult.PLAYER_TWO_WINS) {
								a2.fitness += 1;
								a1.fitness -= 1;
								break;
							}

							assert result != MoveResult.INVALID_MOVE;
						}
					}
				}
			}
		};

		trainer.edgeMutationRate = 0.4f;
		trainer.nodeMutationRate = 0.2f;
		trainer.weightStdDev = .1f;

		Scanner in = new Scanner(System.in);
		while (true) {
			int n = in.nextInt();

			if (n < 0) {
				break;
			}

			for (int i = 1; i <= n; i++) {
				if (trainer.generationNumber() % 30 == 0) {
					System.out.println(trainer);
				}

				trainer.generateNext();
			}
			System.out.println(trainer);
		}

		System.out.println(trainer);
		System.out.println(Agent.exportGraph(trainer.getBest()));

		while (true) {
			TicTacToe ttt = new TicTacToe();
			Agent best = trainer.bestInCurrentGeneration();

			Player userPlayer = RANDOM.nextBoolean() ? Player.ONE : Player.TWO;
			System.out.println("YOU ARE " + ((userPlayer == Player.ONE) ? "X" : "O"));
			System.out.println(ttt);
			while (true) {
				int move;

				if (ttt.currentPlayer() == userPlayer) {
					move = in.nextInt();

					assert ttt.isValidMove(move);
				} else {
					float[] input = new float[ttt.vectorSize() + 1];
					ttt.toVector(userPlayer.other(), input, 1);
					input[0] = 1;

					float[] output = new float[9];
					best.propagate(input, output);

					move = indexOfMax(output);

					if (!ttt.isValidMove(move)) {
						System.out.println("TRIED ILLEGAL MOVE " + move);
						while (!ttt.isValidMove(move)) {
							move = RANDOM.nextInt(0, 9);
						}
					}
				}

				MoveResult r = ttt.play(move);

				System.out.println(ttt);
				if (r != MoveResult.VALID_MOVE) {
					System.out.println(r);
					System.out.println('\n');
					System.out.println('\n');
					System.out.println('\n');
					break;
				}
				System.out.println();
			}

		}
	}

	private static final int choseIndexProportional(float[] arr) {
		float totalSum = 0;
		for (float x : arr) {
			assert x >= 0;
			totalSum += x;
		}

		assert totalSum > 0;

		float p = RANDOM.nextFloat(totalSum);

		float sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (sum > p) {
				return i;
			}
		}

		return arr.length - 1;
	}

	private static int indexOfMax(float[] arr) {
		assert arr.length > 0;

		int max = 0;

		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[max]) {
				max = i;
			}
		}

		return max;
	}

}
