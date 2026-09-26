package com.dubiouscandle.candlecore.collections;

import java.util.Iterator;

public class OrderedSet<T extends Comparable<T>> implements Iterable<T> {
	private final Node nil = new Node(null); // sentinel representing all leaves
	private Node root;
	public int size;

	/**
	 * creates a new empty set
	 */
	public OrderedSet() {
		nil.color = BLACK;
		root = nil;
	}

	/**
	 * adds the specified element into this tree set
	 * 
	 * @param e the element to add
	 * @return true of the element was added (it was not previously in this set)
	 */
	public boolean add(T e) {
		Node z = new Node(e);
		Node y = nil;
		Node x = root;

		while (x != nil) {
			y = x;
			int cmp = z.value.compareTo(x.value);
			if (cmp == 0) {
				return false;
			} else if (cmp < 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		z.parent = y;
		if (y == nil) {
			root = z;
		} else if (z.value.compareTo(y.value) < 0) {
			y.left = z;
		} else {
			y.right = z;
		}

		z.color = RED;
		size++;

		rbInsertFixup(z);
		return true;
	}

	/**
	 * removes the specified element from this tree set
	 * 
	 * @param e the element to remove
	 * @return true if the element was found and removed
	 */
	public boolean remove(T e) {
		Node z = search(e);
		if (z == null) {
			return false;
		}

		Node y = z;
		boolean yOriginalColor = y.color;
		Node x;

		if (z.left == nil) {
			x = z.right;
			transplant(z, z.right);
		} else if (z.right == nil) {
			x = z.left;
			transplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}

		size--;
		if (yOriginalColor == BLACK) {
			rbDeleteFixup(x);
		}
		return true;
	}

	private void transplant(Node u, Node v) {
		if (u.parent == null || u.parent == nil) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	/**
	 * restores this rb tree properties after deletion.
	 */
	private void rbDeleteFixup(Node x) {
		while (x != root && x.color == BLACK) {
			if (x == x.parent.left) {
				Node w = x.parent.right; // sibling of x
				if (w.color == RED) {
					// Case 1: x's sibling is red.
					w.color = BLACK;
					x.parent.color = RED;
					leftRotate(x.parent);
					w = x.parent.right;
				}
				if (w.left.color == BLACK && w.right.color == BLACK) {
					// Case 2: both of w's children are black.
					w.color = RED;
					x = x.parent;
				} else {
					if (w.right.color == BLACK) {
						// Case 3: w's left child is red and right child is black.
						w.left.color = BLACK;
						w.color = RED;
						rightRotate(w);
						w = x.parent.right;
					}
					// Case 4: w's right child is red.
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.right.color = BLACK;
					leftRotate(x.parent);
					x = root;
				}
			} else { // mirror image for when x is right child
				Node w = x.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					x.parent.color = RED;
					rightRotate(x.parent);
					w = x.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					x = x.parent;
				} else {
					if (w.left.color == BLACK) {
						w.right.color = BLACK;
						w.color = RED;
						leftRotate(w);
						w = x.parent.left;
					}
					w.color = x.parent.color;
					x.parent.color = BLACK;
					w.left.color = BLACK;
					rightRotate(x.parent);
					x = root;
				}
			}
		}
		x.color = BLACK;
	}

	/**
	 * finds the minimum node from the subtree.
	 */
	private Node minimum(Node node) {
		while (node.left != nil) {
			node = node.left;
		}
		return node;
	}

	/**
	 * finds the node associated with the element e
	 * 
	 * @param e the element to find the node of
	 * @return the Node if it exists or null if it does not
	 */
	private Node search(T e) {
		Node x = root;
		while (x != nil) {
			int cmp = e.compareTo(x.value);
			if (cmp == 0) {
				return x;
			} else if (cmp < 0) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return null;
	}

	/**
	 * @param e the element to find
	 * @return if this set contains the specified element
	 */
	public boolean contains(T e) {
		return search(e) != null;
	}

	/**
	 * restores rb tree properties after insertion
	 * 
	 * @param z
	 */
	private void rbInsertFixup(Node z) {
		while (z.parent.color == RED) {
			if (z.parent == z.parent.parent.left) {
				Node y = z.parent.parent.right;
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						z = z.parent;
						leftRotate(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rightRotate(z.parent.parent);
				}
			} else {
				Node y = z.parent.parent.left;
				if (y.color == RED) {
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						z = z.parent;
						rightRotate(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);
				}
			}
		}
		root.color = BLACK;
	}

	/**
	 * rotates this tree left around the node x
	 */
	private void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != nil) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nil || x.parent == null) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	/**
	 * rotates this tree right around the node x
	 */
	private void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != nil) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == nil || x.parent == null) {
			root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	private class Node {
		T value;
		Node left, right, parent;
		boolean color; // RED = false, BLACK = true

		Node(T value) {
			this.value = value;
			left = nil;
			right = nil;
			parent = nil;
		}
	}

	public static final boolean RED = false;
	public static final boolean BLACK = true;

	@Override
	public Iterator<T> iterator() {
		return new TreeIterator();
	}

	private class TreeIterator implements Iterator<T> {
		@SuppressWarnings("unchecked")
		Node[] stack = (Node[]) new Object[maxHeight()];
		Node cur;
		int i = 0;

		TreeIterator() {
			cur = root;
			while (cur != nil) {
				stack[i] = cur;
				i++;
				cur = cur.left;
			}
		}

		@Override
		public boolean hasNext() {
			return i != 0;
		}

		@Override
		public T next() {
			i--;

			Node x = stack[i];
			cur = x.right;
			while (cur != nil) {
				stack[i] = cur;
				i++;
				cur = cur.left;
			}
			stack[i] = null;
			return x.value;
		}
	}

	private int maxHeight() {
		int x = size + 1;
		int maxHeight = 0;
		while (x > 1) {
			x >>= 1;
			maxHeight++;
		}
		maxHeight <<= 1;
		if (maxHeight < size + 1) {
			maxHeight++;
		}
		return maxHeight;
	}

	@Override
	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (T value : this) {
			if (first) {
				first = false;
			} else {
				sb.append(',').append(' ');
			}
			sb.append(value);
		}
		sb.append(']');
		return sb.toString();
	}
}
