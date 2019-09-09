package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.JSONSink;

public class VersionSplitInnerJSONizer<ValueT> implements InnerJSONizer<ValueT> {

	private IntP predicate;

	private InnerJSONizer<? super ValueT> ifFalse;

	private InnerJSONizer<? super ValueT> ifTrue;

	public VersionSplitInnerJSONizer(IntP predicate, InnerJSONizer<? super ValueT> ifFalse,
			InnerJSONizer<? super ValueT> ifTrue) {
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

	public InnerJSONizer<? super ValueT> getIfFalse() {
		return ifFalse;
	}

	public void setIfFalse(InnerJSONizer<? super ValueT> ifFalse) {
		this.ifFalse = ifFalse;
	}

	public InnerJSONizer<? super ValueT> getIfTrue() {
		return ifTrue;
	}

	public void setIfTrue(InnerJSONizer<? super ValueT> ifTrue) {
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
