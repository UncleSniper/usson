package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public class Null implements StaticJSON {

	public static final StaticJSON instance = new Null();

	public Null() {}

	@Override
	public void jsonize(JSONSink sink, int version) throws IOException {
		sink.foundNull();
	}

}
