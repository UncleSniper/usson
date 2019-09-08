package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public final class JSONLens<ValueT> implements JSONizer<ValueT>, DeJSONizer<ValueT> {

	private final JSONizer<ValueT> jsonizer;

	private final DeJSONizer<ValueT> dejsonizer;

	public JSONLens(JSONizer<ValueT> jsonizer, DeJSONizer<ValueT> dejsonizer) {
		this.jsonizer = jsonizer;
		this.dejsonizer = dejsonizer;
	}

	public JSONizer<ValueT> getJSONizer() {
		return jsonizer;
	}

	public DeJSONizer<ValueT> getDeJSONizer() {
		return dejsonizer;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		jsonizer.jsonize(value, sink, version);
	}

	@Override
	public JSONState<ValueT> dejsonize(int version) {
		return dejsonizer.dejsonize(version);
	}

}
