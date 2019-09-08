package org.unclesniper.json.j8;

public class ByteIteratorAdapter implements ByteIterator, ShortIterator, IntIterator, LongIterator {

	private final ByteIterator iterator;

	public ByteIteratorAdapter(ByteIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public byte nextByte() {
		return iterator.nextByte();
	}

	@Override
	public short nextShort() {
		return iterator.nextByte();
	}

	@Override
	public int nextInt() {
		return iterator.nextByte();
	}

	@Override
	public long nextLong() {
		return iterator.nextByte();
	}

}
