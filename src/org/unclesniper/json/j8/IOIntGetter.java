package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOIntGetter<BaseT> {

	int getInt(BaseT base) throws IOException;

}
