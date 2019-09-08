package org.unclesniper.json.lens;

import java.io.IOException;

public interface JSONStateBase<StateT extends JSONStateBase<StateT>> {

	StateT foundBoolean(boolean value) throws IOException, UnexpectedJSONException;

	StateT foundNull() throws IOException, UnexpectedJSONException;

	StateT foundString(String value) throws IOException, UnexpectedJSONException;

	StateT foundInteger(long value) throws IOException, UnexpectedJSONException;

	StateT foundFraction(double value) throws IOException, UnexpectedJSONException;

	StateT beginObject() throws IOException, UnexpectedJSONException;

	StateT endObject() throws IOException, UnexpectedJSONException;

	StateT beginArray() throws IOException, UnexpectedJSONException;

	StateT endArray() throws IOException, UnexpectedJSONException;

}
