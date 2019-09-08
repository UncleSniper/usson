package org.unclesniper.json.lens;

import org.unclesniper.json.JSONSink;

public class EmptySequenceJSONizer<ValueT> implements SequenceJSONizer<ValueT> {

	public EmptySequenceJSONizer() {}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) {}

}
