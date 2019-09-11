package org.unclesniper.json.j8;

import java.io.IOException;

public class IOFloatIteratorAdapter implements IOFloatIterator, IODoubleIterator {

	private final IOFloatIterator iterator;

	public IOFloatIteratorAdapter(IOFloatIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() throws IOException {
		return iterator.hasNext();
	}

	@Override
	public float nextFloat() throws IOException {
		return iterator.nextFloat();
	}

	@Override
	public double nextDouble() throws IOException {
		return iterator.nextFloat();
	}

}
