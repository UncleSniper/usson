package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOLongIterable {

	IOLongIterator longIterator() throws IOException;

}
