package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOFloatIterable extends IODoubleIterable {

	IOFloatIterator floatIterator() throws IOException;

}
