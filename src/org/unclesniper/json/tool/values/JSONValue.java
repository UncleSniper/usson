package org.unclesniper.json.tool.values;

import org.unclesniper.json.JSON;

public class JSONValue extends Value {

	private final JSON json;

	public JSONValue(JSON json) {
		this.json = json;
	}

	public JSON getJSON() {
		return json;
	}

	public Type getType() {
		return Type.JSON;
	}

}
