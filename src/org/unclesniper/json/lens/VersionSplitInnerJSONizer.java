package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOIntP;

public class VersionSplitInnerJSONizer<ValueT> implements InnerJSONizer<ValueT> {

	private IOIntP predicate;

	private InnerJSONizer<? super ValueT> ifFalse;

	private InnerJSONizer<? super ValueT> ifTrue;

	public VersionSplitInnerJSONizer(IOIntP predicate, InnerJSONizer<? super ValueT> ifFalse,
			InnerJSONizer<? super ValueT> ifTrue) {
		this.predicate = predicate;
		this.ifFalse = ifFalse;
		this.ifTrue = ifTrue;
	}

	public IOIntP getPredicate() {
		return predicate;
	}

	public void setPredicate(IOIntP predicate) {
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
