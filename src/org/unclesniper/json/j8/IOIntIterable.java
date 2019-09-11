package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOIntIterable extends IOLongIterable {

	IOIntIterator intIterator() throws IOException;

}
