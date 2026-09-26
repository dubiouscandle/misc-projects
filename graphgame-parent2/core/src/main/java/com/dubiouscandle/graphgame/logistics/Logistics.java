package com.dubiouscandle.graphgame.logistics;

import java.util.PriorityQueue;

import com.badlogic.gdx.utils.ObjectSet;
import com.dubiouscandle.graphgame.Resource;

public class Logistics {
	private ObjectSet<Node> nodes = new ObjectSet<>();
	private ObjectSet<Edge> edges = new ObjectSet<>();
	private PriorityQueue<Edge> queue = new PriorityQueue<>();
	protected long curTimeNano = 0;

	public Logistics() {
	}

	public void update(float delta) {
		curTimeNano += (long) ((double) delta * 1_000_000_000.0);

		while (!queue.isEmpty() && queue.peek().finishedTransfer(curTimeNano)) {
			Edge edge = queue.poll();

			if (!edges.contains(edge)) {
				continue;
			}

			boolean deposited = edge.deposit();

			if (!deposited) {
				edge.delayRetry(1L);
				queue.add(edge);
				continue;
			}

			if (edge.wake(curTimeNano)) {
				queue.add(edge);
			}

			for (Edge next : edge.to.outgoing) {
				boolean found = next.wake(curTimeNano);
				if (found) {
					queue.add(next);
				}
			}
		}
	}

	private void wake(Node node) {
		for (Edge edge : node.outgoing) {
			boolean found = edge.wake(curTimeNano);
			if (found) {
				queue.add(edge);
			}
		}
	}

	public void deposit(Node node, Resource resource, int amount) {
		node.resources.add(resource, amount);

		wake(node);
	}

//	public void withdraw(Node node, Resource resource, int amount) {
//		node.resources.add(resource, -amount);
//
//		for (Edge edge : node.outgoing) {
//			edge.wake(curTimeNano);
//		}
//	}

	public Node createNode() {
		Node node = new Node();
		nodes.add(node);
		return node;
	}

	public void removeNode(Node node) {
		if (!nodes.remove(node)) {
			throw new IllegalArgumentException("Node not found.");
		}

		for (Edge edge : node.outgoing) {
			edges.remove(edge);
			edge.to.ingoing.removeValue(edge, false);
		}
		for (Edge edge : node.ingoing) {
			edges.remove(edge);
			edge.from.outgoing.removeValue(edge, false);
		}

	}

	public Edge createEdge(Node from, Node to, float transferRate) {
		Edge edge = new Edge(from, to, (long) (1_000_000_000.0 / (double) transferRate));
		edges.add(edge);
		from.outgoing.add(edge);
		to.ingoing.add(edge);

		wake(from);

		return edge;
	}

	public void removeEdge(Edge edge) {
		if (!edges.remove(edge)) {
			throw new IllegalArgumentException("Edge not found.");
		}
		edge.from.outgoing.removeValue(edge, false);
		edge.to.ingoing.removeValue(edge, false);
	}
}
