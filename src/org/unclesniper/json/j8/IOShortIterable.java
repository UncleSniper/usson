package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOShortIterable extends IOIntIterable {

	IOShortIterator shortIterator() throws IOException;

}
