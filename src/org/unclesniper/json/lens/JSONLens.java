package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectSink;

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
	public JSONState dejsonize(IOObjectSink<? super ValueT> sink, int version, JSONState parent) {
		return dejsonizer.dejsonize(sink, version, parent);
	}

}
