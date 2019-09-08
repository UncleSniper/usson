package org.unclesniper.json.j8;

public class FloatIteratorAdapter implements FloatIterator, DoubleIterator {

	private final FloatIterator iterator;

	public FloatIteratorAdapter(FloatIterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public float nextFloat() {
		return iterator.nextFloat();
	}

	@Override
	public double nextDouble() {
		return iterator.nextFloat();
	}

}
