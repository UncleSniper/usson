package org.unclesniper.json;

import java.io.IOException;

public class JSONInteger extends JSONNumber {

	private int value;

	public JSONInteger(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	public Integer numberValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundInteger(value);
	}

	public int getJSONType() {
		return JSON.TYPE_INTEGER;
	}

}
