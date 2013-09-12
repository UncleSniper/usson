package org.unclesniper.json;

public class JSONString extends JSONPrimitive {

	private String value;

	public JSONString(String value) {
		if(value == null)
			throw new NullPointerException("String value cannot be null");
		this.value = value;
	}

	public String stringValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) {
		sink.foundString(value);
	}

	public int getJSONType() {
		return JSON.TYPE_STRING;
	}

}
