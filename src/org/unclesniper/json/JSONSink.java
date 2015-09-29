package org.unclesniper.json;

import java.io.IOException;

public interface JSONSink {

	void foundBoolean(boolean value) throws IOException;

	void foundNull() throws IOException;

	void foundString(String value) throws IOException;

	void foundInteger(long value) throws IOException;

	void foundFraction(double value) throws IOException;

	void beginObject() throws IOException;

	void endObject() throws IOException;

	void beginArray() throws IOException;

	void endArray() throws IOException;

}
