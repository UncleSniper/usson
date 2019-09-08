package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class FloatArrayIterator implements FloatIterator {

	private final float[] array;

	private int index;

	public FloatArrayIterator(float[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public float nextFloat() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
