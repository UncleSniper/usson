package org.unclesniper.json;

import java.io.IOException;

public class JSONNull extends JSONPrimitive {

	public static final JSONNull instance = new JSONNull();

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundNull();
	}

	public int getJSONType() {
		return JSON.TYPE_NULL;
	}

}
