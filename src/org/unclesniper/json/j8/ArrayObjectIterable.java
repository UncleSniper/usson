package org.unclesniper.json.j8;

public class ArrayObjectIterable<ElementT> implements ObjectIterable<ElementT> {

	private ElementT[] array;

	public ArrayObjectIterable(ElementT[] array) {
		this.array = array;
	}

	public ElementT[] getArray() {
		return array;
	}

	public void setArray(ElementT[] array) {
		this.array = array;
	}

	@Override
	public ObjectIterator<ElementT> objectIterator() {
		return new ArrayObjectIterator<ElementT>(array);
	}

}
