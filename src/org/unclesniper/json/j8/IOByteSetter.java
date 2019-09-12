package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOByteSetter<BaseT> {

	void setByte(BaseT base, byte value) throws IOException;

}
