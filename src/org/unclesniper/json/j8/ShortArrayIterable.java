package org.unclesniper.json.j8;

public class ShortArrayIterable implements ShortIterable {

	private short[] array;

	public ShortArrayIterable(short[] array) {
		this.array = array;
	}

	public short[] getArray() {
		return array;
	}

	public void setArray(short[] array) {
		this.array = array;
	}

	@Override
	public ShortIterator shortIterator() {
		return new ShortArrayIterator(array);
	}

	@Override
	public IntIterator intIterator() {
		return new ShortIteratorAdapter(new ShortArrayIterator(array));
	}

	@Override
	public LongIterator longIterator() {
		return new ShortIteratorAdapter(new ShortArrayIterator(array));
	}

}
