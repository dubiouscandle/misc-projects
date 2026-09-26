package com.dubiouscandle.minineat;

import java.util.Random;

public class Mutator {
	private static final Random RANDOM = new Random();
	private final Random random;

	public Mutator(long seed) {
		random = new Random(seed);
	}

	public Mutator() {
		random = RANDOM;
	}

	public void createEdge(Agent agent, float weight) {
		int from = random.nextInt(agent.nodeCount);
		int to = random.nextInt(agent.inCount, agent.nodeCount);

		if (agent.canCreateEdge(from, to)) {
			agent.createEdge(from, to, weight);
		}
	}

	public void createNode(Agent agent) {
		if (agent.edgeCount == 0) {
			return;
		}

		int edgeIndex = random.nextInt(agent.edgeCount);

		int to = agent.getTo(edgeIndex);
		float weight = agent.getWeight(edgeIndex);

		int node = agent.createNode();

		agent.setWeight(edgeIndex, 1);
		agent.redirectEdge(edgeIndex, node);
		agent.createEdge(node, to, weight);
	}

	public void mutateWeight(Agent agent, float delta) {
		if (agent.edgeCount == 0) {
			return;
		}

		agent.changeWeight(random.nextInt(agent.edgeCount), delta);
	}
}
