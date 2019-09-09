package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.JSONSink;

public class VersionSplitJSONizer<ValueT> implements JSONizer<ValueT> {

	private IntP predicate;

	private JSONizer<? super ValueT> ifFalse;

	private JSONizer<? super ValueT> ifTrue;

	public VersionSplitJSONizer(IntP predicate, JSONizer<? super ValueT> ifFalse,
			JSONizer<? super ValueT> ifTrue) {
		this.predicate = predicate;
		this.ifFalse = ifFalse;
		this.ifTrue = ifTrue;
	}

	public IntP getPredicate() {
		return predicate;
	}

	public void setPredicate(IntP predicate) {
		this.predicate = predicate;
	}

	public JSONizer<? super ValueT> getIfFalse() {
		return ifFalse;
	}

	public void setIfFalse(JSONizer<? super ValueT> ifFalse) {
		this.ifFalse = ifFalse;
	}

	public JSONizer<? super ValueT> getIfTrue() {
		return ifTrue;
	}

	public void setIfTrue(JSONizer<? super ValueT> ifTrue) {
		this.ifTrue = ifTrue;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(predicate.testInt(version))
			ifTrue.jsonize(value, sink, version);
		else
			ifFalse.jsonize(value, sink, version);
	}

}
