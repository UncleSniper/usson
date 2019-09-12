package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public class InnerJSONLensInto<ValueT> implements InnerJSONizer<ValueT>, InnerDeJSONizerInto<ValueT> {

	private final InnerJSONizer<ValueT> jsonizer;

	private final InnerDeJSONizerInto<ValueT> dejsonizer;

	public InnerJSONLensInto(InnerJSONizer<ValueT> jsonizer, InnerDeJSONizerInto<ValueT> dejsonizer) {
		this.jsonizer = jsonizer;
		this.dejsonizer = dejsonizer;
	}

	public InnerJSONizer<ValueT> getJSONizer() {
		return jsonizer;
	}

	public InnerDeJSONizerInto<ValueT> getDeJSONizer() {
		return dejsonizer;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		jsonizer.jsonize(value, sink, version);
	}

	@Override
	public JSONState dejsonize(ValueT value, int version, JSONState parent) {
		return dejsonizer.dejsonize(value, version, parent);
	}

	@Override
	public String getPropertyName() {
		return dejsonizer.getPropertyName();
	}

}
