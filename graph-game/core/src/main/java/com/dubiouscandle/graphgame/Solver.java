package com.dubiouscandle.graphgame;

import java.util.ArrayList;

import collision.PartitioningGrid;
import physics.Cell;

public class Solver {
	private double worldSize = 200;

	private PartitioningGrid<Node> partitioningGrid = new PartitioningGrid<>(worldSize, 2);
	private ArrayList<Cell> cells = new ArrayList<>();

	public ArrayList<Cell> getCells() {
		return cells;
	}

	private double elapsedTime = 0;

	private double stepInterval = 1.0 / 300;

	public void update(double timeSeconds) {
		elapsedTime += timeSeconds;

		while (elapsedTime >= stepInterval) {
			step();
			elapsedTime -= stepInterval;
		}
	}

	private void step() {
		// apply independent forces
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			cell.handleStructuralForce();
			cell.handleBondingForce();
			cell.handleFrictionForce(stepInterval);
		}

		// apply dependent forces
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i + 1; j < cells.size(); j++) {
				Cell cellA = cells.get(i);
				Cell cellB = cells.get(j);

				cellA.handleElectrostaticForce(cellB);
			}

		}

		// update motion and add all nodes to the partitioningGrid
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			cell.handleMotion(stepInterval);

			partitioningGrid.populate(cell.getNodes());
		}

		partitioningGrid.forEachAdjacent((nodeA, nodeB) -> {
			if (nodeA.isColliding(nodeB)) {
				System.out.println(nodeA.hashCode() + " " + nodeB.hashCode());
			}
		});

		partitioningGrid.clear();
	}

	public double getWorldSize() {
		return worldSize;
	}

	public void setWorldSize(double worldSize) {
		this.worldSize = worldSize;
	}

	public void addCell(Cell cell) {
		this.cells.add(cell);
	}
}
