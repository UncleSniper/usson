package org.unclesniper.json;

public interface JSONSink {

	void foundBoolean(boolean value);

	void foundNull();

	void foundString(String value);

	void foundInteger(int value);

	void foundFraction(double value);

	void beginObject();

	void endObject();

	void beginArray();

	void endArray();

}
