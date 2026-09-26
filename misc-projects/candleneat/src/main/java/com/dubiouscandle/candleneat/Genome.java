package com.dubiouscandle.candleneat;

import java.util.ArrayList;

public class Genome {
	private float fitness = 0;

	private ArrayList<Node> nodes = new ArrayList<>();
	private ArrayList<Edge> edges = new ArrayList<>();

	public Genome() {
	}

	private class Node {
		private int id;
		private int type;
	}

	private class Edge {
		Node from, to;
		float weight;
		boolean enabled;
		int id;
	}
	
	
	
}
