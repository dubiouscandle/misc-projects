package com.dubiouscandle.fluidsimulation;

import java.util.Arrays;

public class Cell {
	public int size;
	public Point[] points;

	public Cell() {
		points = new Point[16];
		size = 0;
	}

	public void add(Point p) {
		if (size == points.length) {
			points = Arrays.copyOf(points, points.length * 2);
		}

		points[size++] = p;
	}
}
