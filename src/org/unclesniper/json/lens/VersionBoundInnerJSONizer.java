package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOIntP;

public class VersionBoundInnerJSONizer<ValueT>
		extends VersionBoundJSONizerBase<ValueT> implements InnerJSONizer<ValueT> {

	public VersionBoundInnerJSONizer(IOIntP predicate, InnerJSONizer<? super ValueT> jsonizer) {
		super(predicate, jsonizer);
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(predicate.testInt(version))
			jsonizer.jsonize(value, sink, version);
	}

}
