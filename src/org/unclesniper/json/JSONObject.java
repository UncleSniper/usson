package org.unclesniper.json;

import java.util.Map;
import java.util.HashMap;

public class JSONObject extends HashMap<String, JSON> implements JSONCompound {

	public boolean takesPairs() {
		return true;
	}

	public void takeElement(JSON value) {
		throw new UnsupportedOperationException("JSON object does not take elements");
	}

	public void takePair(String key, JSON value) {
		if(key == null)
			throw new NullPointerException("Key cannot be null");
		put(key, value == null ? JSONNull.instance : value);
	}

	public void sinkJSON(JSONSink sink) {
		sink.beginObject();
		for(Map.Entry<String, JSON> entry : entrySet()) {
			sink.foundString(entry.getKey());
			entry.getValue().sinkJSON(sink);
		}
		sink.endObject();
	}

	public int getJSONType() {
		return JSON.TYPE_OBJECT;
	}

}
