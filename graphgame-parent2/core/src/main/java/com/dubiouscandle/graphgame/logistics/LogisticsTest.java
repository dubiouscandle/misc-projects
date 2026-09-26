package com.dubiouscandle.graphgame.logistics;

import com.dubiouscandle.graphgame.Resource;

public class LogisticsTest {

	public static void main(String[] args) {
		Logistics logistics = new Logistics();
		Node source = logistics.createNode();
		Node destination = logistics.createNode();

		// Give source some resources
		logistics.deposit(source, Resource.BASE, 2);
		logistics.deposit(source, Resource.RED, 1);
		logistics.deposit(source, Resource.YELLOW, 1);
		logistics.deposit(source, Resource.PURPLE, 0);
		
		// Set destination max limits
		destination.maxResources.set(Resource.BASE, 1);
		destination.maxResources.set(Resource.RED, 1);
		destination.maxResources.set(Resource.YELLOW, 1);
		destination.maxResources.set(Resource.PURPLE, 1);

		// Create edge
		Edge edge = logistics.createEdge(source, destination, 1f); // 1 unit per second
		source.outgoing.add(edge);
		destination.ingoing.add(edge);

		// Run simulation steps
		System.out.println("--- Simulation Start ---");
		for (int i = 0; i < 10; i++) {
			logistics.update(1.0f); // simulate 1 second
			printState(i + 1, source, destination);
		}
	}

	private static void printState(int step, Node source, Node dest) {
		System.out.println("Step " + step + ":");
		for (Resource r : Resource.VALUES) {
			System.out.printf("  %s: source=%d, dest=%d\n", r, source.resources.get(r), dest.resources.get(r));
		}
		System.out.println();
	}
}
