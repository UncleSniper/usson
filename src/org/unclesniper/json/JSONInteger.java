package org.unclesniper.json;

import java.io.IOException;

public class JSONInteger extends JSONNumber {

	private long value;

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
