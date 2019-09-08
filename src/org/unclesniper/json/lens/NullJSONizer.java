package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public class NullJSONizer<ValueT> implements JSONizer<ValueT> {

	public NullJSONizer() {}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		sink.foundNull();
	}

}
