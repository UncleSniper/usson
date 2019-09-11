package org.unclesniper.json.j8;

public interface IntIterable extends IOIntIterable, LongIterable {

	@Override
	IntIterator intIterator();

}
