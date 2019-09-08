package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class BooleanArrayIterator implements BooleanIterator {

	private final boolean[] array;

	private int index;

	public BooleanArrayIterator(boolean[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public boolean next() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
