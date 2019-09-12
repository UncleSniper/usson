package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOLongSetter<BaseT> {

	void setLong(BaseT base, long value) throws IOException;

}
