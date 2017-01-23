package org.unclesniper.json.tool.values;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONString;
import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.JSONFraction;

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

	public boolean isTrue() {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				return ((JSONString)json).stringValue().length() > 0;
			case JSON.TYPE_INTEGER:
				return ((JSONInteger)json).longValue() != 0l;
			case JSON.TYPE_FRACTION:
				{
					double d = ((JSONFraction)json).doubleValue();
					return d != 0.0 && d != -0.0 && d != Double.NaN;
				}
			case JSON.TYPE_BOOLEAN:
				return ((JSONBoolean)json).booleanValue();
			case JSON.TYPE_NULL:
				return false;
			case JSON.TYPE_ARRAY:
			case JSON.TYPE_OBJECT:
				return true;
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
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
