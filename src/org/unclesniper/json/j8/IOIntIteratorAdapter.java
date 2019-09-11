package org.unclesniper.json.j8;

import java.io.IOException;

public class IOIntIteratorAdapter implements IOIntIterator, IOLongIterator {

	private final IOIntIterator iterator;

	public IOIntIteratorAdapter(IOIntIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() throws IOException {
		return iterator.hasNext();
	}

	@Override
	public int nextInt() throws IOException {
		return iterator.nextInt();
	}

	@Override
	public long nextLong() throws IOException {
		return iterator.nextInt();
	}

}
