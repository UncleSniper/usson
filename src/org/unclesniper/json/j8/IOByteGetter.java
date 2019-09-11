package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOByteGetter<BaseT> {

	byte getByte(BaseT base) throws IOException;

}
