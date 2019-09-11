package org.unclesniper.json.j8;

public interface ObjectIterable<ElementT> extends IOObjectIterable<ElementT> {

	@Override
	ObjectIterator<ElementT> objectIterator();

}
