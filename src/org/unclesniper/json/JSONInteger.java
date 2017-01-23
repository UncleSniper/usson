package org.unclesniper.json;

import java.io.IOException;

public class JSONInteger extends JSONNumber {

	public static final JSONInteger ZERO = new JSONInteger(0l);

	private final long value;

	public JSONInteger(long value) {
		this.value = value;
	}

	public long longValue() {
		return value;
	}

	public Long numberValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundInteger(value);
	}

	public int getJSONType() {
		return JSON.TYPE_INTEGER;
	}

}
