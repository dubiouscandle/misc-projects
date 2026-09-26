package com.dubiouscandle.fluidsimulation;

import java.util.function.BiConsumer;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;

public class PartitioningGrid {
	protected final int width, height, numCells;
	protected final float factor;
	protected Cell[] cells;
	protected OrderedSet<Cell> active;

	public PartitioningGrid(float cellSize, float worldWidth, float worldHeight) {
		factor = 1.0f / cellSize;
		width = MathUtils.ceil(worldWidth / cellSize);
		height = MathUtils.ceil(worldWidth / cellSize);
		numCells = width * height;

		cells = new Cell[numCells];
	}

	public void add(Point point) {
		int gx = (int) (factor * point.x);
		int gy = (int) (factor * point.y);

		Cell cell = cells[gy * width + gx];

		active.add(cell);

		cell.add(point);
	}

	public void forEachAdjacent(BiConsumer<Point, Point> consumer) {		
		for(Cell cell : active) {
			active.orderedItems().items[i].size = 0;
		}
		
		active.clear();
	}
}
