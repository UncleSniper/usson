package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class IntArrayIterator implements IntIterator {

	private final int[] array;

	private int index;

	public IntArrayIterator(int[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public int nextInt() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
