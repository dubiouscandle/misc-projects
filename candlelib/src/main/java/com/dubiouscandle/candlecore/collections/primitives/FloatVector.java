package com.dubiouscandle.candlecore.collections.primitives;

import com.dubiouscandle.candlecore.algorithm.Arrays;

/**
 * No checks are performed for bounds or null values in this class. It is the
 * responsibility of the user to ensure: - Array indices are within bounds (0 <=
 * index < size). - Null values are handled appropriately if necessary. Any
 * out-of-bounds access or invalid operations will not result in runtime
 * exceptions (e.g., ArrayIndexOutOfBoundsException).
 */
public class FloatVector {
	/**
	 * the items in this vector
	 */
	public float[] items;
	/**
	 * the size of this vector
	 */
	public int size;

	public FloatVector() {
		items = new float[16];
		size = 0;
	}

	/**
	 * adds the specified element to this vector
	 *
	 * @param e the element to add
	 */
	public void add(float value) {
		if (size == items.length) {
			items = Arrays.copyOf(items, size * 2);
		}

		items[size] = value;
		size++;
	}

	/**
	 * @param index the index of the element to get
	 * @return the element at the specified index
	 */
	public float get(int index) {
		assert 0 <= index && index < size;

		return items[index];
	}

	/**
	 * removes the element at the specified index by overwriting it with the last
	 * element in the vector
	 *
	 * @param index the index of the element to remove
	 */
	public void removeUnordered(int index) {
		assert 0 <= index && index < size;

		size--;
		items[index] = items[size];
	}

	/**
	 * removes the element at the specified index by shifting all other elements
	 * down
	 *
	 * @param index the index of the element to remove
	 */
	public void remove(int index) {
		assert 0 <= index && index < size;

		size--;
		System.arraycopy(items, index + 1, items, index, size - index);
	}

	/**
	 * sets the size of this vector to zero
	 */
	public void clear() {
		size = 0;
	}

	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		sb.append(items[0]);
		for (int i = 1; i < size; i++) {
			sb.append(',').append(' ').append(items[i]);
		}
		sb.append(']');
		return sb.toString();
	}
}
