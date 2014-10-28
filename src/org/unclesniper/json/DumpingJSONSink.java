package org.unclesniper.json;

public class DumpingJSONSink implements JSONSink {

	public void foundBoolean(boolean value) {
		System.err.println("foundBoolean(" + value + ")");
		System.err.flush();
	}

	public void foundNull() {
		System.err.println("foundNull()");
		System.err.flush();
	}

	public void foundString(String value) {
		System.err.println("foundString(" + JSONString.escapeString(value, true) + ")");
		System.err.flush();
	}

	public void foundInteger(int value) {
		System.err.println("foundInteger(" + value + ")");
		System.err.flush();
	}

	public void foundFraction(double value) {
		System.err.println("foundFraction(" + value + ")");
		System.err.flush();
	}

	public void beginObject() {
		System.err.println("beginObject()");
		System.err.flush();
	}

	public void endObject() {
		System.err.println("endObject()");
		System.err.flush();
	}

	public void beginArray() {
		System.err.println("beginArray()");
		System.err.flush();
	}

	public void endArray() {
		System.err.println("endArray()");
		System.err.flush();
	}

}
