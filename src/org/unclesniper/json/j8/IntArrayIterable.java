package org.unclesniper.json.j8;

public class IntArrayIterable implements IntIterable {

	private int[] array;

	public IntArrayIterable(int[] array) {
		this.array = array;
	}

	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	@Override
	public IntIterator intIterator() {
		return new IntArrayIterator(array);
	}

	@Override
	public LongIterator longIterator() {
		return new IntIteratorAdapter(new IntArrayIterator(array));
	}

}
