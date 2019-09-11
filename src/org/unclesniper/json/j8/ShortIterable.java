package org.unclesniper.json.j8;

public interface ShortIterable extends IOShortIterable, IntIterable {

	@Override
	ShortIterator shortIterator();

}
