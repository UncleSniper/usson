package org.unclesniper.json.j8;

import java.io.IOException;

public class IOShortIteratorAdapter implements IOShortIterator, IOIntIterator, IOLongIterator {

	private final IOShortIterator iterator;

	public IOShortIteratorAdapter(IOShortIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() throws IOException {
		return iterator.hasNext();
	}

	@Override
	public short nextShort() throws IOException {
		return iterator.nextShort();
	}

	@Override
	public int nextInt() throws IOException {
		return iterator.nextShort();
	}

	@Override
	public long nextLong() throws IOException {
		return iterator.nextShort();
	}

}
