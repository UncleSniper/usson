package org.unclesniper.json.j8;

import java.util.NoSuchElementException;

public class DoubleArrayIterator implements DoubleIterator {

	private final double[] array;

	private int index;

	public DoubleArrayIterator(double[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public double nextDouble() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

}
