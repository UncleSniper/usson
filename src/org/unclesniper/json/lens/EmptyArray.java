package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public class EmptyArray implements StaticJSON {

	public static final StaticJSON instance = new EmptyArray();

	public EmptyArray() {}

	@Override
	public void jsonize(JSONSink sink, int version) throws IOException {
		sink.beginArray();
		sink.endArray();
	}

}
