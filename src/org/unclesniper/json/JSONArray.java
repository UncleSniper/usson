package org.unclesniper.json;

import java.util.Vector;

public class JSONArray extends Vector<JSON> implements JSONCompound {

	public boolean takesPairs() {
		return false;
	}

	public void takeElement(JSON value) {
		add(value == null ? JSONNull.instance : value);
	}

	public void takePair(String key, JSON value) {
		throw new UnsupportedOperationException("JSON array does not take key/value pairs");
	}

	public void sinkJSON(JSONSink sink) {
		sink.beginArray();
		for(JSON element : this)
			element.sinkJSON(sink);
		sink.endArray();
	}

	public int getJSONType() {
		return JSON.TYPE_ARRAY;
	}

}
