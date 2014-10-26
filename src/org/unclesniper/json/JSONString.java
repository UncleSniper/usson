package org.unclesniper.json;

import java.io.IOException;

public class JSONString extends JSONPrimitive {

	private static final char[] hexChars = "0123456789ABCDEF".toCharArray();

	private String value;

	public JSONString(String value) {
		if(value == null)
			throw new NullPointerException("String value cannot be null");
		this.value = value;
	}

	public String stringValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundString(value);
	}

	public int getJSONType() {
		return JSON.TYPE_STRING;
	}

	public static String escapeString(String value, boolean quote) {
		StringBuilder builder = new StringBuilder();
		if(quote)
			builder.append('"');
		int i, length = value.length();
		char c;
		for(i = 0; i < length; ++i)
			switch(c = value.charAt(i)) {
				case '"':
				case '\\':
					builder.append('\\');
					builder.append(c);
					break;
				case '\b':
					builder.append("\\b");
					break;
				case '\f':
					builder.append("\\f");
					break;
				case '\n':
					builder.append("\\n");
					break;
				case '\r':
					builder.append("\\r");
					break;
				case '\t':
					builder.append("\\t");
					break;
				default:
					if(c < ' ') {
						builder.append("\\u");
						int code = (int)c;
						for(i = 12; i >= 0; i -= 4)
							builder.append(JSONString.hexChars[(code >> i) & 0xF]);
					}
					else
						builder.append(c);
					break;
			}
		if(quote)
			builder.append('"');
		return builder.toString();
	}

}
