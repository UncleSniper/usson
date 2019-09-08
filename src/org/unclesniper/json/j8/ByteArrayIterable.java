package org.unclesniper.json.j8;

public class ByteArrayIterable implements ByteIterable {

	private byte[] array;

	public ByteArrayIterable(byte[] array) {
		this.array = array;
	}

	public byte[] getArray() {
		return array;
	}

	public void setArray(byte[] array) {
		this.array = array;
	}

	@Override
	public ByteIterator byteIterator() {
		return new ByteArrayIterator(array);
	}

	@Override
	public ShortIterator shortIterator() {
		return new ByteIteratorAdapter(new ByteArrayIterator(array));
	}

	@Override
	public IntIterator intIterator() {
		return new ByteIteratorAdapter(new ByteArrayIterator(array));
	}

	@Override
	public LongIterator longIterator() {
		return new ByteIteratorAdapter(new ByteArrayIterator(array));
	}

}
