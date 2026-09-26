package com.dubiouscandle.minineat;

import java.util.Arrays;
import java.util.Scanner;

public class Agent implements Comparable<Agent> {
	private final ActivationFunction fun;

	private static final int MUL4_MASK = ~0b11;

	public float fitness = 0;

	protected final int inCount, outCount;

	private int[][] tos;
	private float[][] weights;
	private int[] outDegrees;
	private int[] inDegrees;

	protected int nodeCount;

	private int[] fromsList;
	private int[] idxList;
	protected int edgeCount;

	private float[] activations;
	private int[] inDegreesCopy;
	private int[] stack;

	public Agent(Agent agent) {
		fun = agent.fun;
		inCount = agent.inCount;
		outCount = agent.outCount;

		nodeCount = agent.nodeCount;
		int len1 = agent.tos.length;

		tos = agent.tos.clone();
		weights = agent.weights.clone();
		for (int i = 0; i < tos.length; i++) {
			tos[i] = tos[i].clone();
			weights[i] = weights[i].clone();
		}

		outDegrees = agent.outDegrees.clone();
		inDegrees = agent.inDegrees.clone();
		activations = new float[len1];
		inDegreesCopy = new int[len1];
		stack = new int[len1];

		fromsList = agent.fromsList.clone();
		idxList = agent.idxList.clone();
		edgeCount = agent.edgeCount;
	}

	public Agent(int inCount, int outCount, ActivationFunction fun) {
		this.fun = fun;
		this.inCount = inCount;
		this.outCount = outCount;

		nodeCount = inCount + outCount;
		int len1 = (nodeCount + 3) & MUL4_MASK;
		tos = new int[len1][4];
		weights = new float[len1][4];
		outDegrees = new int[len1];
		inDegrees = new int[len1];
		activations = new float[len1];
		inDegreesCopy = new int[len1];
		stack = new int[len1];

		fromsList = new int[16];
		idxList = new int[16];
		edgeCount = 0;
	}

	public int getTo(int index) {
		return tos[fromsList[index]][idxList[index]];
	}

	public int getFrom(int index) {
		return fromsList[index];
	}

	public float getWeight(int index) {
		return weights[fromsList[index]][idxList[index]];
	}

	public void propagate(float[] input, float[] output) {
		for (int i = 0; i < inCount; i++) {
			stack[i] = i;
			activations[i] = input[i];
		}
		int top = inCount;

		System.arraycopy(inDegrees, 0, inDegreesCopy, 0, nodeCount);

		while (top > 0) {
			top--;
			int cur = stack[top];

			for (int i = 0; i < outDegrees[cur]; i++) {
				int adj = tos[cur][i];
				float weight = weights[cur][i];

				activations[adj] = (inDegrees[adj] == inDegreesCopy[adj]) ? (weight * activations[cur])
						: (activations[adj] + weight * activations[cur]);
				inDegreesCopy[adj]--;

				if (inDegreesCopy[adj] == 0) {
					activations[adj] = fun.get(activations[adj]);
					stack[top] = adj;
					top++;
				}
			}
		}

		for (int i = 0; i < output.length; i++) {
			output[i] = activations[i + inCount];
		}
	}

	protected boolean canCreateEdge(int from, int to) {
		if (from == to)
			return false;

		for (int i = 0; i < outDegrees[from]; i++) {
			if (tos[from][i] == to)
				return false;
		}

		System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegreesCopy.length);

		int top = 1;
		stack[0] = to;
		while (top > 0) {
			top--;
			int cur = stack[top];

			for (int i = 0; i < outDegrees[cur]; i++) {
				int adj = tos[cur][i];

				if (inDegreesCopy[adj] != inDegrees[adj]) {
					continue;
				}

				if (adj == from) {
					return false;
				}

				stack[top] = adj;
				top++;

				inDegreesCopy[adj]--;
			}
		}

		return true;
	}

	protected void createEdge(int from, int to, float weight) {
		if (!canCreateEdge(from, to)) {
			System.out.println(Arrays.toString(inDegrees));
			System.out.println(from + " " + to);
			System.out.println(Agent.exportGraph(this));
			assert false;
		}
		if (edgeCount >= fromsList.length) {
			fromsList = resized(fromsList);
			idxList = resized(idxList);
		}

		fromsList[edgeCount] = from;
		idxList[edgeCount] = outDegrees[from];

		if (outDegrees[from] >= tos[from].length) {
			weights[from] = resized(weights[from]);
			tos[from] = resized(tos[from]);
		}

		weights[from][outDegrees[from]] = weight;
		tos[from][outDegrees[from]] = to;

		outDegrees[from]++;
		inDegrees[to]++;
		edgeCount++;
	}

	protected int createNode() {
		if (nodeCount >= outDegrees.length) {
			int oldTosLen = tos.length;

			outDegrees = resized(outDegrees);
			inDegrees = resized(inDegrees);
			tos = resized(tos);
			weights = resized(weights);

			int len = stack.length + 4;
			stack = new int[len];
			activations = new float[len];
			inDegreesCopy = new int[len];

			for (int i = oldTosLen; i < tos.length; i++) {
				tos[i] = new int[4];
				weights[i] = new float[4];
			}
		}

		return nodeCount++;
	}

	protected void changeWeight(int index, float delta) {
		weights[fromsList[index]][idxList[index]] += delta;
	}

	protected void setWeight(int index, float weight) {
		weights[fromsList[index]][idxList[index]] = weight;
	}

	protected void redirectEdge(int index, int to) {
		assert canCreateEdge(fromsList[index], to);

		inDegrees[tos[fromsList[index]][idxList[index]]]--;
		inDegrees[to]++;
		tos[fromsList[index]][idxList[index]] = to;
	}

	@Override
	public int compareTo(Agent o) {
		return Float.compare(o.fitness, fitness);
	}

	private static int[] resized(int[] arr) {
		int[] resized = new int[arr.length + 4];
		System.arraycopy(arr, 0, resized, 0, arr.length);
		return resized;
	}

	private static float[] resized(float[] arr) {
		float[] resized = new float[arr.length + 4];
		System.arraycopy(arr, 0, resized, 0, arr.length);
		return resized;
	}

	private static int[][] resized(int[][] arr) {
		int[][] resized = new int[arr.length + 4][];
		System.arraycopy(arr, 0, resized, 0, arr.length);
		return resized;
	}

	private static float[][] resized(float[][] arr) {
		float[][] resized = new float[arr.length + 4][];
		System.arraycopy(arr, 0, resized, 0, arr.length);
		return resized;
	}

	@Override
	public String toString() {
		return "fitness = " + fitness + ", edgeCount = " + edgeCount + ", nodeCount = " + nodeCount;
	}

	/**
	 * turns the specified agent's graph into a string representation
	 * 
	 * @param agent
	 * @return<br>
	 *             The first four numbers are the nodeCount and edgeCount in that
	 *             order. <br>
	 *             The next edgeCount lines are the in the form <br>
	 *             x[1] y[1] z[1]<br>
	 *             x[2] y[2] z[2]<br>
	 *             ...<br>
	 *             x[edgeCount] y[edgeCount] z[edgeCount]. <br>
	 *             Meaning, that there exists a directed edge i from x[i] to y[i]
	 *             with weight z[i];
	 */
	public static String exportGraph(Agent agent) {
		StringBuilder sb = new StringBuilder();

		sb.append(agent.nodeCount).append(' ').append(agent.edgeCount).append('\n');
		for (int i = 0; i < agent.edgeCount; i++) {
			sb.append(agent.fromsList[i]).append(' ');
			sb.append(agent.tos[agent.fromsList[i]][agent.idxList[i]]).append(' ');
			sb.append(agent.weights[agent.fromsList[i]][agent.idxList[i]]).append('\n');
		}

		return sb.toString();
	}

	public Agent(String topology, int inCount, int outCount, ActivationFunction fun) {
		this.fun = fun;
		this.inCount = inCount;
		this.outCount = outCount;

		Scanner in = new Scanner(topology);
		nodeCount = in.nextInt();
		edgeCount = in.nextInt();
		for (int i = 0; i < nodeCount; i++) {
			createNode();
		}
		for (int i = 0; i < edgeCount; i++) {
			createEdge(in.nextInt(), in.nextInt(), in.nextFloat());
		}
		in.close();
	}
}
