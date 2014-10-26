package org.unclesniper.json;

import java.io.IOException;

public class JSONBoolean extends JSONPrimitive {

	private boolean value;

	public JSONBoolean(boolean value) {
		this.value = value;
	}

	public boolean booleanValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundBoolean(value);
	}

	public int getJSONType() {
		return JSON.TYPE_BOOLEAN;
	}

}
