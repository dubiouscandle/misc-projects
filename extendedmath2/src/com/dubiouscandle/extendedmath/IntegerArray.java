package com.dubiouscandle.extendedmath;

import java.util.Arrays;

public class IntegerArray {
	private int[] values;
	private int size;

	public IntegerArray() {
		size = 0;
		values = new int[16];
	}

	/**
	 * Appends value to the end of this array
	 * 
	 * @param value
	 */
	public void add(int value) {
		ensureCapacity();

		values[size] = value;
		size++;
	}

	public void set(int index, int value) {
		checkIndex(index);

		values[index] = value;
	}

	/**
	 * removes the element at the specified index. if ordered is true, the last
	 * element is moved in place of the removed element.
	 * 
	 * @param index
	 * @param ordered
	 */
	public void remove(int index, boolean ordered) {
		checkIndex(index);

		if (ordered) {
			System.arraycopy(values, index + 1, values, index, size - index - 1);
		} else {
			values[index] = values[size - 1];
		}
		size--;
	}

	public int size() {
		return size;
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}

	private void ensureCapacity() {
		if (values.length == size) {
			int[] resized = new int[size * 3 / 2];

			System.arraycopy(values, 0, resized, 0, values.length);

			values = resized;
		}
	}

	/**
	 * sorts this array from i to j
	 * 
	 * @param i
	 * @param j
	 */
	public void sort(int i, int j) {
		checkIndex(i);
		checkIndex(j);
		if (i > j) {
			throw new IllegalArgumentException("invalid range (" + i + ", " + j + ")");
		}

		Arrays.sort(values, i, j);
	}

	/**
	 * sorts this array
	 */
	public void sort() {
		Arrays.sort(values, 0, size);
	}

	/**
	 * 
	 * @param value
	 * @return the index of the first element greater than or equal to value.
	 *         Returns size if value if greater than all values in the array.
	 */
	public int bisectLeft(int value) {
		int min = 0;
		int max = size - 1;

		while (min <= max) {
			int mid = (min + max) / 2;

			if (values[mid] >= value) {
				max = mid - 1;
			} else {
				min = mid + 1;
			}
		}

		return min;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(size * 4);

		sb.append('[');
		if (size > 0) {
			sb.append(values[0]);
		}
		for (int i = 1; i < size; i++) {
			sb.append(',').append(' ').append(values[i]);
		}
		sb.append(']');

		return sb.toString();
	}
}
