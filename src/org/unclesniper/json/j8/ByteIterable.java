package org.unclesniper.json.j8;

public interface ByteIterable extends IOByteIterable, ShortIterable {

	@Override
	ByteIterator byteIterator();

}
