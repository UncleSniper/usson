package org.unclesniper.json.j8;

import java.io.IOException;

public class IOByteIteratorAdapter implements IOByteIterator, IOShortIterator, IOIntIterator, IOLongIterator {

	private final IOByteIterator iterator;

	public IOByteIteratorAdapter(IOByteIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() throws IOException {
		return iterator.hasNext();
	}

	@Override
	public byte nextByte() throws IOException {
		return iterator.nextByte();
	}

	@Override
	public short nextShort() throws IOException {
		return iterator.nextByte();
	}

	@Override
	public int nextInt() throws IOException {
		return iterator.nextByte();
	}

	@Override
	public long nextLong() throws IOException {
		return iterator.nextByte();
	}

}
