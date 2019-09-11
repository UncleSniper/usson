package org.unclesniper.json.j8;

import java.io.IOException;

public interface IODoubleGetter<BaseT> {

	double getDouble(BaseT base) throws IOException;

}
