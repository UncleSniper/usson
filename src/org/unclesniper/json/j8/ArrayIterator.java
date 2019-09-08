package org.unclesniper.json.j8;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<ElementT> implements Iterator<ElementT> {

	private final ElementT[] array;

	private int index;

	public ArrayIterator(ElementT[] array) {
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return array != null && index < array.length;
	}

	@Override
	public ElementT next() {
		if(array == null || index >= array.length)
			throw new NoSuchElementException("Array index out of bounds: " + index
					+ " >= " + (array == null ? 0 : array.length));
		return array[index++];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
