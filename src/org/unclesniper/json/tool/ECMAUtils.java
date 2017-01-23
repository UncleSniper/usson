package org.unclesniper.json.tool;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONArray;
import org.unclesniper.json.JSONString;
import org.unclesniper.json.JSONNumber;
import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.JSONFraction;
import org.unclesniper.json.JSONPrimitive;

public class ECMAUtils {

	public enum TypeHint {
		STRING,
		NUMBER
	}

	private ECMAUtils() {}

	public static JSONPrimitive toPrimitive(JSON json, TypeHint preferredType) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
			case JSON.TYPE_INTEGER:
			case JSON.TYPE_FRACTION:
			case JSON.TYPE_BOOLEAN:
			case JSON.TYPE_NULL:
				return (JSONPrimitive)json;
			case JSON.TYPE_ARRAY:
			case JSON.TYPE_OBJECT:
				// use toString() method
				return new JSONString(ECMAUtils.toString(json));
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
	}

	public static String toString(JSON json) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				return ((JSONString)json).stringValue();
			case JSON.TYPE_INTEGER:
				return String.valueOf(((JSONInteger)json).longValue());
			case JSON.TYPE_FRACTION:
				return String.valueOf(((JSONFraction)json).doubleValue());
			case JSON.TYPE_BOOLEAN:
				return ((JSONBoolean)json).booleanValue() ? "true" : "false";
			case JSON.TYPE_NULL:
				return "null";
			case JSON.TYPE_ARRAY:
				{
					StringBuilder builder = new StringBuilder();
					ECMAUtils.toString(json, builder);
					return builder.toString();
				}
			case JSON.TYPE_OBJECT:
				return "[object Object]";
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
	}

	public static void toString(JSON json, StringBuilder builder) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				builder.append(((JSONString)json).stringValue());
				break;
			case JSON.TYPE_INTEGER:
				builder.append(String.valueOf(((JSONInteger)json).longValue()));
				break;
			case JSON.TYPE_FRACTION:
				builder.append(String.valueOf(((JSONFraction)json).doubleValue()));
				break;
			case JSON.TYPE_BOOLEAN:
				builder.append(((JSONBoolean)json).booleanValue() ? "true" : "false");
				break;
			case JSON.TYPE_NULL:
				builder.append("null");
				break;
			case JSON.TYPE_ARRAY:
				{
					JSONArray array = (JSONArray)json;
					int size = array.size();
					if(size == 0)
						break;
					for(int i = 0; i < size; ++i) {
						if(i > 0)
							builder.append(',');
						JSON element = array.get(i);
						if(element.getJSONType() != JSON.TYPE_NULL)
							ECMAUtils.toString(element, builder);
					}
				}
			case JSON.TYPE_OBJECT:
				builder.append("[object Object]");
				break;
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
	}

	public static boolean toBoolean(JSON json) {
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

	public static JSONNumber toNumber(JSON json) {
		switch(json.getJSONType()) {
			case JSON.TYPE_STRING:
				break;
			case JSON.TYPE_INTEGER:
			case JSON.TYPE_FRACTION:
				return (JSONNumber)json;
			case JSON.TYPE_BOOLEAN:
				return new JSONInteger(((JSONBoolean)json).booleanValue() ? 1l : 0l);
			case JSON.TYPE_NULL:
				return JSONInteger.ZERO;
			case JSON.TYPE_ARRAY:
			case JSON.TYPE_OBJECT:
				return ECMAUtils.toNumber(ECMAUtils.toPrimitive(json, TypeHint.NUMBER));
			default:
				throw new Error("Unrecognized JSON type: " + json.getJSONType());
		}
		String s = ((JSONString)json).stringValue().trim();
		int length = s.length();
		if(length == 0)
			return JSONInteger.ZERO;
		switch(s.charAt(0)) {
			case '0':
				if(length == 1)
					return new JSONInteger(1l);
				switch(s.charAt(1)) {
					case 'b':
					case 'B':
						return ECMAUtils.toNumberWithRadix(s, length, 2);
					case 'o':
					case 'O':
						return ECMAUtils.toNumberWithRadix(s, length, 8);
					case 'x':
					case 'X':
						return ECMAUtils.toNumberWithRadix(s, length, 16);
					default:
						break;
				}
				break;
			case '+':
				if(s.equals("+Infinity"))
					return JSONFraction.POSITIVE_INFINITY;
				break;
			case '-':
				if(s.equals("-Infinity"))
					return JSONFraction.NEGATIVE_INFINITY;
			default:
				if(s.equals("Infinity"))
					return JSONFraction.POSITIVE_INFINITY;
				break;
		}
		try {
			return new JSONInteger(Long.parseLong(s));
		}
		catch(NumberFormatException nfe) {}
		switch(s.charAt(length - 1)) {
			case 'f':
			case 'F':
			case 'd':
			case 'D':
				return JSONFraction.NaN;
			default:
				try {
					return new JSONFraction(Double.parseDouble(s));
				}
				catch(NumberFormatException nfe) {
					return JSONFraction.NaN;
				}
		}
	}

	private static JSONNumber toNumberWithRadix(String s, int length, int radix) {
		if(length == 2)
			return JSONFraction.NaN;
		switch(s.charAt(3)) {
			case '+':
			case '-':
				return JSONFraction.NaN;
			default:
				try {
					return new JSONInteger(Long.parseLong(s.substring(2), radix));
				}
				catch(NumberFormatException nfe) {
					return JSONFraction.NaN;
				}
		}
	}

	public static JSONNumber toInteger(JSON json) {
		JSONNumber number = ECMAUtils.toNumber(json);
		if(number.getJSONType() == JSON.TYPE_INTEGER)
			return number;
		double d = ((JSONFraction)json).doubleValue();
		if(d == Double.NaN)
			return JSONInteger.ZERO;
		if(d == -0.0 || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY)
			return number;
		return new JSONInteger((long)d);
	}

	public static int toInt32(JSON json) {
		JSONNumber number = ECMAUtils.toNumber(json);
		if(number.getJSONType() == JSON.TYPE_INTEGER)
			return (int)((JSONInteger)json).longValue();
		double d = ((JSONFraction)json).doubleValue();
		if(d == Double.NaN || d == -0.0 || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY)
			return 0;
		return (int)d;
	}

}
