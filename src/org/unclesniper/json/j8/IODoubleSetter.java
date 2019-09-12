package org.unclesniper.json.j8;

import java.io.IOException;

public interface IODoubleSetter<BaseT> {

	void setDouble(BaseT base, double value) throws IOException;

}
