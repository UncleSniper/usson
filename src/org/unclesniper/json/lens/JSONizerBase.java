package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public interface JSONizerBase<ValueT> {

	void jsonize(ValueT value, JSONSink sink, int version) throws IOException;

}
