package com.dubiouscandle.candlecore.collections;

import java.util.Iterator;

import com.dubiouscandle.candlecore.algorithm.Arrays;

/**
 * A priority queue implementation using a min-heap. No checks are performed for
 * bounds or null values in this class. It is the responsibility of the user to
 * ensure: - Array indices are within bounds (0 <= index < size). - Null values
 * are handled appropriately if necessary. Any out-of-bounds access or invalid
 * operations will not result in runtime exceptions (e.g.,
 * ArrayIndexOutOfBoundsException).
 * 
 * @param <T>
 */

public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {
	/**
	 * the heap for convenient iteration
	 */
	public T[] heap;
	/**
	 * the size of this queue for convenient iteration
	 */
	public int size;

	/**
	 * creates a new priority queue
	 * 
	 * @param comparator
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue() {
		this.heap = (T[]) new Object[26];
		this.size = 0;
	}

	/**
	 * adds the specified element to this queue
	 * 
	 * @param e
	 */
	public void add(T e) {
		if (size == heap.length) {
			heap = Arrays.copyOf(heap, size << 1);
		}

		heap[size] = e;
		heapifyUp(size);
		size++;
	}

	/**
	 * removes and returns the element at the head of this queue
	 * 
	 * @return the element that was at the head of this queue
	 */
	public T poll() {
		if (size == 0)
			return null;

		T result = heap[0];
		heap[0] = heap[size - 1];
		heap[size - 1] = null;
		size--;

		heapifyDown(0);
		return result;
	}

	private void heapifyUp(int index) {
		while (index > 0) {
			int parentIndex = (index - 1) / 2;
			if (heap[index].compareTo(heap[parentIndex]) >= 0)
				break;
			swap(index, parentIndex);
			index = parentIndex;
		}
	}

	private void heapifyDown(int index) {
		while (true) {
			int leftChild = 2 * index + 1;
			int rightChild = 2 * index + 2;
			int smallest = index;

			if (leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0) {
				smallest = leftChild;
			}
			if (rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0) {
				smallest = rightChild;
			}
			if (smallest == index)
				break;

			swap(index, smallest);
			index = smallest;
		}
	}

	private void swap(int i, int j) {
		T temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	/**
	 * @return the element at the head of this queue
	 */
	public T peek() {
		return heap[0];
	}

	@Override
	public String toString() {
		@SuppressWarnings("unchecked")
		T[] sorted = (T[]) new Object[size];
		System.arraycopy(heap, 0, sorted, 0, size);
		Arrays.sort(sorted, 0, size);
		return Arrays.toString(sorted);
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<>() {
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < size;
			}

			@Override
			public T next() {
				return heap[i++];
			}

			@Override
			public void remove() {

			}
		};
	}

}
