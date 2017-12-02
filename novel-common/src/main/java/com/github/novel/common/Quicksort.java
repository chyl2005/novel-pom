package com.github.novel.common;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * more efficient implements for quicksort. <br />
 * 时间复杂度 O(nlogn)，最坏时间复杂度O(n2)，平均时间复杂度 O(nlogn)，算法不具稳定性
 */
public class Quicksort {
	public static final Random RND = new Random();
	public static final int CUTOFF = 11;

	/**
	 * internal method to sort the array with quick sort algorithm asc. <br />
	 * 
	 * @param arr
	 */
	public static void sort(int arr[]) {
		sort(arr, 0, arr.length - 1);
	}

	public static <T> void sort(T[] array, Comparator<? super T> cmp) {
		sort(array, 0, array.length - 1, cmp);
	}

	/**
	 * quick sort algorithm. <br />
	 * 
	 * @param arr
	 *            an array of Comparable items. <br />
	 */
	public static <T extends Comparable<? super T>> void sort(T[] arr) {
		sort(arr, 0, arr.length - 1);
	}

	/**
	 * internal method to sort the array with quick sort algorithm. <br />
	 * 
	 * @param arr
	 *            an array of Comparable Items. <br />
	 * @param left
	 *            the left-most index of the subarray. <br />
	 * @param right
	 *            the right-most index of the subarray. <br />
	 */
	public static void sort(int arr[], int left, int right) {
		if (left >= right) {
            return;
        }
		// start partitioning
		int index = partition(arr, left, right);
		sort(arr, left, index - 1);// sort the left collection.
		sort(arr, index + 1, right);// sort the right collection
	}

	public static <T> void sort(T[] array, int left, int right,
			Comparator<? super T> cmp) {
		if (right > left) {
			int index = partition(array, left, right, cmp);
			sort(array, left, index - 1, cmp);
			sort(array, index + 1, right, cmp);
		}
	}

	public static <T extends Comparable<? super T>> void sort(T[] array,
			int left, int right) {
		if (right > left) {
			int index = partition(array, left, right);
			sort(array, left, index - 1);
			sort(array, index + 1, right);
		}
	}

	/**
	 * internal method to sort the list with quick sort algorithm. <br />
	 * 
	 * @param list
	 *            an list of Comparable Items. <br />
	 * @param left
	 *            the left-most index of the subList. <br />
	 * @param right
	 *            the right-most index of the subList. <br />
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> list,
			int left, int right) {
		if (left > right) {
            return;
        }
		// start partitioning
		int partition = partition(list, left, right);
		sort(list, left, partition - 1);// sort the left collection.
		sort(list, partition + 1, right);// sort the right collection.

	}

	public static <T> void sort(List<T> list, int left, int right,
			Comparator<? super T> cmp) {
		if (right > left) {
			int index = partition(list, left, right, cmp);
			sort(list, left, index - 1, cmp);
			sort(list, index + 1, right, cmp);
		}
	}

	/**
	 * method to sort an subarray from start to end with insertion sort
	 * algorithm. <br />
	 * 
	 * @param arr
	 *            an array of Comparable items. <br />
	 * @param start
	 *            the lefting position. <br />
	 * @param end
	 *            the end position. <br />
	 */
	public static <T extends Comparable<? super T>> void insertionSort(T[] arr,
			int start, int end) {
		int i;
		for (int j = start + 1; j <= end; j++) {
			T tmp = arr[j];
			for (i = j; i > start && tmp.compareTo(arr[i - 1]) < 0; i--) {
				arr[i] = arr[i - 1];
			}
			arr[i] = tmp;
		}
	}

	/**
	 * the partition of an array
	 * 
	 * @param arr
	 *            the array to be sorted<br />
	 * @param start
	 *            the left position. <br />
	 * @param right
	 *            the right position. <br />
	 * @return partition random partition between left and right
	 */
	private static int partition(int arr[], int left, int right) {
		int index = randint(left, right);
		// find the pivot
		int pivot = arr[index];
		swap(arr, index, right);
		for (int i = index = left; i < right; i++) {
            if (arr[i] < pivot) {
                swap(arr, index++, i);
            }
        }
		swap(arr, right, index);
		return index;
	}

	/**
	 * the partition of an array
	 * 
	 * @param arr
	 *            the array to be sorted implements Comparable<br />
	 * @param start
	 *            the left position. <br />
	 * @param right
	 *            the right position. <br />
	 * @return partition random partition between left and right
	 */
	private static <T extends Comparable<? super T>> int partition(T[] arr,
			int left, int right) {
		int index = randint(left, right);
		// find the pivot
		T pivot = arr[index];
		swap(arr, index, right);
		for (int i = index = left; i < right; i++) {
            if (arr[i].compareTo(pivot) < 0) {
                swap(arr, index++, i);
            }
        }
		swap(arr, right, index);
		return index;
	}

	/**
	 * the partition of an array
	 * 
	 * @param arr
	 *            the array to be sorted implements Comparator<br />
	 * @param start
	 *            the left position. <br />
	 * @param right
	 *            the right position. <br />
	 * @return partition random partition between left and right
	 */
	private static <T> int partition(T[] array, int left, int right,
			Comparator<? super T> cmp) {
		int index = randint(left, right);
		T pivot = array[index];
		swap(array, index, right);
		for (int i = index = left; i < right; ++i) {
            if (cmp.compare(array[i], pivot) < 0) {
                swap(array, index++, i);
            }
        }
		swap(array, index, right);
		return (index);
	}

	private static <T extends Comparable<? super T>> int partition(
			List<T> list, int left, int right) {
		int index = randint(left, right);
		T pivot = list.get(index);
		swap(list, right, index);
		for (int i = left = index; i < right; i++) {
            if (list.get(i).compareTo(pivot) < 0) {
                swap(list, index++, i);
            }
        }
		swap(list, left, index);
		return index;

	}

	private static <T> int partition(List<T> list, int left, int right,
			Comparator<? super T> cmp) {
		int index = randint(left, right);
		T pivot = list.get(index);
		swap(list, index, right);
		for (int i = index = left; i < right; ++i) {
            if (cmp.compare(list.get(i), pivot) < 0) {
                swap(list, index++, i);
            }
        }
		swap(list, index, right);
		return (index);
	}

	/**
	 * method to swap references in an list.<br />
	 * 
	 * @param <T>
	 * 
	 * @param arr
	 *            an array of Objects. <br />
	 * @param idx1
	 *            the index of the first element. <br />
	 * @param idx2
	 *            the index of the second element. <br />
	 */
	public static void swap(int[] arr, int idx1, int idx2) {
		if (idx1 != idx2) {
			int temp = arr[idx1];
			arr[idx1] = arr[idx2];
			arr[idx2] = temp;
		}
	}

	/**
	 * method to swap references in an list.<br />
	 * 
	 * @param <T>
	 * 
	 * @param arr
	 *            an array of Objects. <br />
	 * @param idx1
	 *            the index of the first element. <br />
	 * @param idx2
	 *            the index of the second element. <br />
	 */
	public static <T> void swap(List<T> list, int idx1, int idx2) {
		T l = list.get(idx1);
		list.set(idx1, list.get(idx2));
		list.set(idx2, l);
	}

	/**
	 * method to swap references in an array.<br />
	 * 
	 * @param arr
	 *            an array of Objects. <br />
	 * @param idx1
	 *            the index of the first element. <br />
	 * @param idx2
	 *            the index of the second element. <br />
	 */
	public static <T> void swap(T[] arr, int idx1, int idx2) {
		T tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}

	public static int randint(int left, int end) {
		return left + RND.nextInt(end - left + 1);
	}

	/**
	 * get the median of the left, center and right. <br />
	 * order these and hide the pivot by put it the end of of the array. <br />
	 * 
	 * @param arr
	 *            an array of Comparable items. <br />
	 * @param left
	 *            the most-left index of the subarray. <br />
	 * @param right
	 *            the most-right index of the subarray.<br />
	 * @return T
	 */
	/*
	 * public static <T extends Comparable<? super T>> T median(T[] arr, int
	 * left, int right) {
	 * 
	 * int center = (left + right) >>> 1;
	 * 
	 * if (arr[left].compareTo(arr[center]) > 0) swap(arr, left, center); if
	 * (arr[left].compareTo(arr[right]) > 0) swap(arr, left, right); if
	 * (arr[center].compareTo(arr[right]) > 0) swap(arr, center, right);
	 * 
	 * swap(arr, center, right - 1); return arr[right - 1]; }
	 */

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	public static int getMiddle(int a, int b) {
		return (a + b) >>> 1;
	}
	
	public static void main(String[] args) {
		int arr[]={1,2,6,22,4,7,26,28};
		Quicksort.sort(arr);
		for (int i : arr) {
			System.out.println(i+",");
			
		}
	    AtomicInteger atomicInteger = new AtomicInteger();
	}
}
