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

	public static String typeToHumanReadable(JSON json) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				return "string";
			case JSON.TYPE_INTEGER:
				return "integer";
			case JSON.TYPE_FRACTION:
				return "fraction";
			case JSON.TYPE_BOOLEAN:
				return "boolean";
			case JSON.TYPE_NULL:
				return "null";
			case JSON.TYPE_ARRAY:
				return "array";
			case JSON.TYPE_OBJECT:
				return "object";
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
	}

}
