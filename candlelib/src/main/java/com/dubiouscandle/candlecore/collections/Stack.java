package com.dubiouscandle.candlecore.collections;

public class Stack<T> extends Vector<T> {
	/**
	 * removes and returns the last element pushed into this stack
	 * 
	 * @return the last element
	 */
	public T pop() {
		size--;
		T item = items[size];
		items[size] = null;
		return item;
	}

	/**
	 * adds the specified element to the end of this stack
	 * 
	 * @param e the element to add
	 */
	public void push(T e) {
		add(e);
	}
}
