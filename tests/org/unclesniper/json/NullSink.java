package org.unclesniper.json;

public class NullSink implements JSONSink {

	public void foundBoolean(boolean value) {}

	public void foundNull() {}

	public void foundString(String value) {}

	public void foundInteger(long value) {}

	public void foundFraction(double value) {}

	public void beginObject() {}

	public void endObject() {}

	public void beginArray() {}

	public void endArray() {}

}
