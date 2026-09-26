package com.dubiouscandle.candlecore.algorithm;

import java.lang.reflect.Array;
import java.util.Comparator;

public class Arrays {
	private static final Random RANDOM = new Random();

	/**
	 * creates a new copy of the specified array, either truncating or extending the
	 * array if necessary
	 * 
	 * @param arr       the array to copy
	 * @param newLength the new length of the array
	 * @return a resized copy of the specified array
	 */
	public static int[] copyOf(int[] arr, int newLength) {
		int[] resized = new int[newLength];
		System.arraycopy(arr, 0, resized, 0, Math.min(arr.length, resized.length));
		return resized;
	}

	/**
	 * @see #copyOf(int[], int)
	 */
	public static float[] copyOf(float[] arr, int newLength) {
		float[] resized = new float[newLength];
		System.arraycopy(arr, 0, resized, 0, Math.min(arr.length, resized.length));
		return resized;
	}

	/**
	 * @see #copyOf(int[], int)
	 */
	public static <T> T[] copyOf(T[] arr, int newLength) {
		@SuppressWarnings("unchecked")
		T[] resized = (T[]) Array.newInstance(arr.getClass(), newLength);
		System.arraycopy(arr, 0, resized, 0, Math.min(arr.length, resized.length));
		return resized;
	}

	/**
	 * searches the indices from min (inclusive) to max (inclusive) for the greatest
	 * element less than or equal to key. if the list is not sorted, the behavior of
	 * this method is undefined.
	 * 
	 * @param arr
	 * @param key
	 * @param min
	 * @param max
	 * @return the index of the greatest element less than or equal to key. this
	 *         method will return min-1 if key is less than all elements and max+1
	 *         if key is greater than all elements
	 */
	public static int floor(int[] arr, int key, int min, int max) {
		while (min <= max) {
			int mid = (min + max) / 2;

			if (arr[mid] <= key) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}

		return max;
	}

	/**
	 * searches the indices from min (inclusive) to max (inclusive) for the smallest
	 * element greater than or equal to key. if the list is not sorted, the behavior
	 * of this method is undefined.
	 * 
	 * @param arr
	 * @param key
	 * @param min
	 * @param max
	 * @return the index of the smallest element greater than or equal to key. this
	 *         method will return min-1 if key is less than all elements and max+1
	 *         if key is greater than all elements
	 */
	public static int ceil(int[] arr, int key, int min, int max) {
		while (min <= max) {
			int mid = (max + min) / 2;

			if (arr[mid] < key) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		return min;
	}

	/**
	 * @see #floor(int[] arr, int key, int min, int max)
	 */
	public static int floor(float[] arr, int key, int min, int max) {
		while (min <= max) {
			int mid = (min + max) / 2;

			if (arr[mid] <= key) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}

		return max;
	}

	/**
	 * @see #ceil(int[] arr, int key, int min, int max)
	 */
	public static int ceil(float[] arr, int key, int min, int max) {
		while (min <= max) {
			int mid = (max + min) / 2;

			if (arr[mid] < key) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		return min;
	}

	/**
	 * @see #floor(int[] arr, int key, int min, int max)
	 */
	public static <T extends Comparable<T>> int floor(T[] arr, T key, int min, int max) {
		while (min <= max) {
			int mid = (min + max) / 2;

			if (arr[mid].compareTo(key) <= 0) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}

		return max;
	}

	/**
	 * @see #ceil(int[] arr, int key, int min, int max)
	 */
	public static <T extends Comparable<T>> int ceil(T[] arr, T key, int min, int max) {
		while (min <= max) {
			int mid = (max + min) / 2;

			if (arr[mid].compareTo(key) < 0) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		return min;
	}

	/**
	 * @see #floor(int[] arr, int key, int min, int max)
	 */
	public static <T> int floor(T[] arr, T key, Comparator<T> comparator, int min, int max) {
		while (min <= max) {
			int mid = (min + max) / 2;

			if (comparator.compare(arr[mid], (key)) <= 0) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}

		return max;
	}

	/**
	 * @see #ceil(int[] arr, int key, int min, int max)
	 */
	public static <T> int ceil(T[] arr, T key, Comparator<T> comparator, int min, int max) {
		while (min <= max) {
			int mid = (max + min) / 2;

			if (comparator.compare(arr[mid], (key)) < 0) {
				min = mid + 1;
			} else {
				max = mid - 1;
			}
		}
		return min;
	}

	public static String toString(Object[] arr) {
		new Object();
		StringBuilder sb = new StringBuilder();

		sb.append('[');

		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(',').append(' ');
			}
			sb.append(arr[i]);
		}

		return sb.append(']').toString();
	}

	public static String toString(int[] arr) {
		StringBuilder sb = new StringBuilder();

		sb.append('[');

		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(',').append(' ');
			}
			sb.append(arr[i]);
		}

		return sb.append(']').toString();
	}

	private static final int INSERTION_SORT_THRESHOLD = 16;

	/**
	 * sorts the specified array from indices min (inclusive) to max (exclusive)
	 * using intro sort
	 * 
	 * @param <T>   comparable
	 * @param array the array to sort
	 * @param min   the min index (inclusive)
	 * @param max   the max index (exlusive)
	 */
	public static <T extends Comparable<T>> void sort(T[] array, int min, int max) {
		if (max - min <= INSERTION_SORT_THRESHOLD) {
			insertionSort(array, min, max);
			return;
		}

		int maxDepth = 2 * (31 - Integer.numberOfLeadingZeros(max - min));

		int[] lowStack = new int[maxDepth];
		int[] highStack = new int[maxDepth];
		int[] depthStack = new int[maxDepth];
		lowStack[0] = min;
		highStack[0] = max;
		depthStack[0] = maxDepth;
		int top = 1;

		while (top > 0) {
			top--;
			int start = lowStack[top];
			int end = highStack[top];
			int depth = depthStack[top];

			if (end - start <= INSERTION_SORT_THRESHOLD) {
				insertionSort(array, start, end);
			} else if (depth == 1) {
				heapSort(array, start, end);
			} else {
				int pivot = partition(array, start, end - 1);
				lowStack[top] = start;
				highStack[top] = pivot;
				depthStack[top] = depth - 1;
				top++;
				lowStack[top] = pivot + 1;
				highStack[top] = end;
				depthStack[top] = depth - 1;
				top++;
			}
		}
	}

	/**
	 * sorts the specified array from indices min (inclusive) to max (exclusive)
	 * using insertion sort
	 * 
	 * @param <T>   comparable
	 * @param array the array to sort
	 * @param min   the min index (inclusive)
	 * @param max   the max index (exlusive)
	 */
	private static <T extends Comparable<T>> void insertionSort(T[] array, int min, int max) {
		for (int i = min + 1; i < max; i++) {
			T key = array[i];
			int j = i - 1;
			while (j >= min && array[j].compareTo(key) > 0) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = key;
		}
	}

	/**
	 * sorts the specified array from indices min (inclusive) to max (exclusive)
	 * using heap sort
	 * 
	 * @param <T>   comparable
	 * @param array the array to sort
	 * @param min   the min index (inclusive)
	 * @param max   the max index (exlusive)
	 */
	private static <T extends Comparable<T>> void heapSort(T[] array, int min, int max) {
		int n = max - min;
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(array, n, i, min);
		}
		for (int i = n - 1; i > 0; i--) {
			swap(array, min, min + i);
			heapify(array, i, 0, min);
		}
	}

	/**
	 * partitions the specified array from indices min (inclusive) to max
	 * (inclusive) for quicksort
	 * 
	 * @param <T>   comparable
	 * @param array the array to partition
	 * @param min   the min index (inclusive)
	 * @param max   the max index (inclusive)
	 */
	private static <T extends Comparable<T>> int partition(T[] array, int min, int max) {
		int mid = min + (max - min) / 2;
		if (array[min].compareTo(array[mid]) > 0)
			swap(array, min, mid);
		if (array[min].compareTo(array[max]) > 0)
			swap(array, min, max);
		if (array[mid].compareTo(array[max]) > 0)
			swap(array, mid, max);

		T pivot = array[mid];
		swap(array, mid, max);

		int i = min - 1;
		for (int j = min; j < max; j++) {
			if (array[j].compareTo(pivot) <= 0) {
				i++;
				swap(array, i, j);
			}
		}
		swap(array, i + 1, max);
		return i + 1;
	}

	private static <T extends Comparable<T>> void heapify(T[] array, int n, int i, int offset) {
		int largest = i, left = 2 * i + 1, right = 2 * i + 2;
		if (left < n && array[offset + left].compareTo(array[offset + largest]) > 0)
			largest = left;
		if (right < n && array[offset + right].compareTo(array[offset + largest]) > 0)
			largest = right;
		if (largest != i) {
			swap(array, offset + i, offset + largest);
			heapify(array, n, largest, offset);
		}
	}

	private static <T> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static <T extends Comparable<T>> void shuffle(T[] array, int min, int max) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = RANDOM.nextInt(i + 1);
			T temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	/**
	 * checks if the array is sorted from min (inclusive) to max (exclusive)
	 * 
	 * @param <T>
	 * @param array the array to check
	 * @param min   the min index (inclusive)
	 * @param max   the max index (exlusive)
	 * @return true if the array is sorted
	 */
	public static <T extends Comparable<T>> boolean isSorted(T[] array, int min, int max) {
		for (int i = min; i < max - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				return false;
			}
		}

		return true;
	}
}
