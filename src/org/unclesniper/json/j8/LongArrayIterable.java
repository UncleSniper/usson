package org.unclesniper.json.j8;

public class LongArrayIterable implements LongIterable {

	private long[] array;

	public LongArrayIterable(long[] array) {
		this.array = array;
	}

	public long[] getArray() {
		return array;
	}

	public void setArray(long[] array) {
		this.array = array;
	}

	@Override
	public LongIterator longIterator() {
		return new LongArrayIterator(array);
	}

}
