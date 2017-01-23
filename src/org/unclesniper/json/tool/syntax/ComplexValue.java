package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONArray;
import org.unclesniper.json.JSONString;
import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.JSONFraction;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public abstract class ComplexValue extends Syntax {

	public ComplexValue(int offset) {
		super(offset);
	}

	public abstract Value eval(TransformationContext context) throws TransformationException;

	public static long coerceToInteger(JSON json) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				{
					String s = ((JSONString)json).stringValue();
					try {
						return Long.parseLong(s);
					}
					catch(NumberFormatException nfe) {}
					try {
						return (long)Double.parseDouble(s);
					}
					catch(NumberFormatException nfe) {}
					return 0l;
				}
			case JSON.TYPE_INTEGER:
				return ((JSONInteger)json).longValue();
			case JSON.TYPE_FRACTION:
				return (long)((JSONFraction)json).doubleValue();
			case JSON.TYPE_BOOLEAN:
				return ((JSONBoolean)json).booleanValue() ? 1l : 0l;
			case JSON.TYPE_NULL:
				return 0l;
			case JSON.TYPE_ARRAY:
				{
					JSONArray array = (JSONArray)json;
					if(array.size() != 1)
						return 0l;
					return ComplexValue.coerceToInteger(array.get(0));
				}
			case JSON.TYPE_OBJECT:
				return 0l;
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
	}

}
