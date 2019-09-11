package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOByteIterable extends IOShortIterable {

	IOByteIterator byteIterator() throws IOException;

}
