package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class ByteArrayIterator implements ByteIterator {

	private final byte[] array;

	private int index;

	public ByteArrayIterator(byte[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public byte nextByte() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
