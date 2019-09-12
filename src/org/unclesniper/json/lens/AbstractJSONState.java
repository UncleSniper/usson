package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.ObjectSink;

public abstract class AbstractJSONState implements JSONState {

	protected JSONState parent;

	public AbstractJSONState(JSONState parent) {
		this.parent = parent;
	}

	protected String getExpected() {
		return null;
	}

	@Override
	public JSONState getParent() {
		return parent;
	}

	@Override
	public void getChainAsPath(ObjectSink<UnexpectedJSONException.JSONPath> sink) {}

	@Override
	public JSONState foundBoolean(boolean value) throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("boolean", getExpected());
	}

	@Override
	public JSONState foundNull() throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("null", getExpected());
	}

	@Override
	public JSONState foundString(String value) throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("string", getExpected());
	}

	@Override
	public JSONState foundInteger(long value) throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("integer", getExpected());
	}

	@Override
	public JSONState foundFraction(double value) throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("fraction", getExpected());
	}

	@Override
	public JSONState beginObject() throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("object", getExpected());
	}

	@Override
	public JSONState endObject() throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("end-of-object", getExpected());
	}

	@Override
	public JSONState beginArray() throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("array", getExpected());
	}

	@Override
	public JSONState endArray() throws IOException, UnexpectedJSONException {
		throw new UnexpectedJSONStructureException("end-of-array", getExpected());
	}

}
