package org.unclesniper.json.j8;

public class ShortIteratorAdapter implements ShortIterator, IntIterator, LongIterator {

	private final ShortIterator iterator;

	public ShortIteratorAdapter(ShortIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public short nextShort() {
		return iterator.nextShort();
	}

	@Override
	public int nextInt() {
		return iterator.nextShort();
	}

	@Override
	public long nextLong() {
		return iterator.nextShort();
	}

}
