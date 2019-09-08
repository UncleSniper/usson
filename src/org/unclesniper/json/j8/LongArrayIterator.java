package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class LongArrayIterator implements LongIterator {

	private final long[] array;

	private int index;

	public LongArrayIterator(long[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public long nextLong() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
