package org.unclesniper.json.j8;

import java.io.IOException;

public interface IOObjectGetter<BaseT, PropertyT> {

	PropertyT getProperty(BaseT base) throws IOException;

}
