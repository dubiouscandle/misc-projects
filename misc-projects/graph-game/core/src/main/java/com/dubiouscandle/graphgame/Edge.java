package com.dubiouscandle.graphgame;

import java.util.Objects;

public class Edge {
	protected Node nodeA, nodeB;

	public Edge(Node nodeA, Node nodeB) {
		if (nodeA == nodeB) {
			throw new IllegalArgumentException("Cannot bond to self.");
		}
		
		this.nodeA = nodeA;
		this.nodeB = nodeB;
	}
	
	public void handleBondingForce() {
		float dx = nodeA.x - nodeB.x;
		float dy = nodeA.y - nodeB.y;

		float distance = (float)Math.sqrt(dx * dx + dy * dy);

		if (distance == 0)
			return;

		float displacement = distance - Constants.IDEAL_BOND_LENGTH;

		float ndx = dx / distance;
		float ndy = dy / distance;

		float fx = ndx * displacement * Constants.BONDING_CONSTANT;
		float fy = ndy * displacement * Constants.BONDING_CONSTANT;

		nodeA.ax -= fx;
		nodeA.ay -= fy;

		nodeB.ax += fx;
		nodeB.ay += fy;
	}
}
