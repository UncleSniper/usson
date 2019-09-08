package org.unclesniper.json.j8;

public class IntIteratorAdapter implements IntIterator, LongIterator {

	private final IntIterator iterator;

	public IntIteratorAdapter(IntIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public int nextInt() {
		return iterator.nextInt();
	}

	@Override
	public long nextLong() {
		return iterator.nextInt();
	}

}
