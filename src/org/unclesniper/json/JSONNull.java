package org.unclesniper.json;

public class JSONNull extends JSONPrimitive {

	public static final JSONNull instance = new JSONNull();

	public void sinkJSON(JSONSink sink) {
		sink.foundNull();
	}

	public int getJSONType() {
		return JSON.TYPE_NULL;
	}

}
