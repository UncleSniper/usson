package org.unclesniper.json.j8;

public interface ObjectIterator<ElementT> extends IteratorBase, IOObjectIterator<ElementT> {

	@Override
	ElementT next();

}
