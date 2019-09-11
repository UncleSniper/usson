package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOObjectIterator<ElementT> extends IOIteratorBase {

	ElementT next() throws IOException;

}
