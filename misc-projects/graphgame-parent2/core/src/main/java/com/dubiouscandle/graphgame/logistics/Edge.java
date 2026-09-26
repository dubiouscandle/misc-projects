package com.dubiouscandle.graphgame.logistics;

import com.dubiouscandle.graphgame.Resource;

public class Edge implements Comparable<Edge> {
	protected final Node from, to;

	private final long transferTimeNano;
	private long finishTimeNano;

	private Resource currentResource = Resource.BASE;

	public Edge(Node from, Node to, long transferTimeNano) {
		this.from = from;
		this.to = to;
		this.transferTimeNano = transferTimeNano;
	}

	protected boolean finishedTransfer(long curTimeNano) {
		return curTimeNano >= finishTimeNano;
	}

	protected boolean wake(long curTimeNano) {
		boolean found = findResource();

		if (found) {
			from.resources.add(currentResource, -1);
			finishTimeNano = curTimeNano + transferTimeNano;
		}

		return found;
	}

	protected boolean deposit() {
		if (to.resources.get(currentResource) >= to.maxResources.get(currentResource)) {
			return false;
		}

		to.resources.add(currentResource, 1);
		return true;
	}

	protected void delayRetry(long delayNano) {
		finishTimeNano += delayNano;
	}

	private boolean findResource() {
		for (int i = 0; i < Resource.VALUES.length; i++) {
			int j = (currentResource.ordinal() + i + 1) % Resource.VALUES.length;
			Resource resource = Resource.VALUES[j];

			if (from.resources.get(resource) >= 1) {
				currentResource = resource;
				return true;
			}
		}

		return false;
	}

	@Override
	public int compareTo(Edge o) {
		return Long.compare(finishTimeNano, o.finishTimeNano);
	}
}
