package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.ObjectSink;

public interface JSONState {

	JSONState getParent();

	void getChainAsPath(ObjectSink<UnexpectedJSONException.JSONPath> sink);

	JSONState foundBoolean(boolean value) throws IOException, UnexpectedJSONException;

	JSONState foundNull() throws IOException, UnexpectedJSONException;

	JSONState foundString(String value) throws IOException, UnexpectedJSONException;

	JSONState foundInteger(long value) throws IOException, UnexpectedJSONException;

	JSONState foundFraction(double value) throws IOException, UnexpectedJSONException;

	JSONState beginObject() throws IOException, UnexpectedJSONException;

	JSONState endObject() throws IOException, UnexpectedJSONException;

	JSONState beginArray() throws IOException, UnexpectedJSONException;

	JSONState endArray() throws IOException, UnexpectedJSONException;

}
