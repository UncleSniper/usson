package org.unclesniper.json.j8;

import java.util.Iterator;

public class ArrayIterable<ElementT> implements Iterable<ElementT> {

	private ElementT[] array;

	public ArrayIterable(ElementT[] array) {
		this.array = array;
	}

	public ElementT[] getArray() {
		return array;
	}

	public void setArray(ElementT[] array) {
		this.array = array;
	}

	@Override
	public Iterator<ElementT> iterator() {
		return new ArrayIterator<ElementT>(array);
	}

}
