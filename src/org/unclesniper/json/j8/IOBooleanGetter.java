package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOBooleanGetter<BaseT> {

	boolean getBoolean(BaseT base) throws IOException;

}
