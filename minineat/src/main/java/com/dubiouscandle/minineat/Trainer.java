package com.dubiouscandle.minineat;

import java.util.Arrays;
import java.util.Random;

public class Trainer {
	private Random random;
	private Mutator mutator;
	private Agent[] agents;
	private Agent[] nextAgents;
	private Agent best;
	private float[] prefix;
	private FitnessFunction fun;

	private int generationNumber = 0;
	private final int generationSize;
	public float elitism = 0.2f;
	public float nodeMutationRate = 0.05f;
	public float edgeMutationRate = 0.15f;
	public float weightMutationRate = 0.6f;
	public float weightStdDev = 0.1f;

	public Trainer(FitnessFunction fitfun, ActivationFunction actfun, int inCount, int outCount, int generationSize,
			long trainingSeed) {
		this.fun = fitfun;
		this.generationSize = generationSize;

		random = new Random(trainingSeed);
		mutator = new Mutator(random.nextLong());

		agents = new Agent[generationSize];
		nextAgents = new Agent[generationSize];
		prefix = new float[generationSize];

		for (int i = 0; i < generationSize; i++) {
			agents[i] = new Agent(inCount, outCount, actfun);
		}

		best = agents[0];
	}

	public void generateNext() {
		generationNumber++;
		System.arraycopy(agents, 0, nextAgents, 0, nextAgents.length);

		assignFitnessValues(agents);
		Arrays.sort(agents);

		if (agents[0].fitness > best.fitness) {
			best = agents[0];
		}

		generatePrefix();

		int pointer = 0;
		while (pointer < generationSize * elitism) {
			nextAgents[pointer] = new Agent(agents[pointer]);
			pointer++;
		}

		while (pointer < generationSize) {
			Agent agent = new Agent(choseRandomProportional(random.nextFloat()));

			if (random.nextFloat() < nodeMutationRate) {
				mutator.createNode(agent);
			}
			if (random.nextFloat() < edgeMutationRate) {
				mutator.createEdge(agent, weightStdDev * (float) random.nextGaussian());
			}
			if (random.nextFloat() <= weightMutationRate) {
				mutator.mutateWeight(agent, weightStdDev * (float) random.nextGaussian());
			}

			nextAgents[pointer] = agent;
			pointer++;
		}

		System.arraycopy(nextAgents, 0, agents, 0, nextAgents.length);
	}

	private void generatePrefix() {
		assert prefix.length != 0;
		// assuming sorted

		float min = agents[0].fitness;
		prefix[0] = agents[0].fitness;
		for (int i = 1; i < agents.length; i++) {
			prefix[i] = prefix[i - 1] + agents[i].fitness;
			if(agents[i].fitness < min) {
				min = agents[i].fitness;
			}
		}
		float multiplier = 1.0f / (prefix[prefix.length - 1] - min);
		for (int i = 0; i < agents.length; i++) {
			prefix[i] = (prefix[i] - min) * multiplier;
		}
	}

	private Agent choseRandomProportional(float x) {
		int index = Arrays.binarySearch(prefix, x);
		if (index < 0) {
			index = -index - 1;
		}
		return agents[index];
	}

	protected void assignFitnessValues(Agent[] agents) {
		for (Agent agent : agents) {
			agent.fitness = fun.get(agent);
		}
	}

	public Agent getBest() {
		return best;
	}

	public int generationNumber() {
		return generationNumber;
	}

	@FunctionalInterface
	public static interface FitnessFunction {
		public float get(Agent agent);
	}

	@Override
	public String toString() {
		return "generation = " + generationNumber + ", best = [" + best.toString() + "]";
	}

	public Agent bestInCurrentGeneration() {
		Agent max = agents[0];
		
		for(int i = 1; i < agents.length; i++) {
			if(agents[i].fitness > max.fitness) {
				max = agents[i];
			}
		}
		
		return max;
	}
}
