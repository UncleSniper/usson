package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public class JSONLensInto<ValueT> implements JSONizer<ValueT>, DeJSONizerInto<ValueT> {

	private final JSONizer<ValueT> jsonizer;

	private final DeJSONizerInto<ValueT> dejsonizer;

	public JSONLensInto(JSONizer<ValueT> jsonizer, DeJSONizerInto<ValueT> dejsonizer) {
		this.jsonizer = jsonizer;
		this.dejsonizer = dejsonizer;
	}

	public JSONizer<ValueT> getJSONizer() {
		return jsonizer;
	}

	public DeJSONizerInto<ValueT> getDeJSONizer() {
		return dejsonizer;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		jsonizer.jsonize(value, sink, version);
	}

	@Override
	public JSONStateInto dejsonize(ValueT value, int version) {
		return dejsonizer.dejsonize(value, version);
	}

}
