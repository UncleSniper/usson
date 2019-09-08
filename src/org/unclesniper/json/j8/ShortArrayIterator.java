package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class ShortArrayIterator implements ShortIterator {

	private final short[] array;

	private int index;

	public ShortArrayIterator(short[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public short nextShort() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
