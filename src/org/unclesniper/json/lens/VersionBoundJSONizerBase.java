package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IntP;

public abstract class VersionBoundJSONizerBase<ValueT> {

	protected IntP predicate;

	protected InnerJSONizer<? super ValueT> jsonizer;

	public VersionBoundJSONizerBase(IntP predicate, InnerJSONizer<? super ValueT> jsonizer) {
		this.predicate = predicate;
		this.jsonizer = jsonizer;
	}

	public IntP getPredicate() {
		return predicate;
	}

	public void setPredicate(IntP predicate) {
		this.predicate = predicate;
	}

	public InnerJSONizer<? super ValueT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(InnerJSONizer<? super ValueT> jsonizer) {
		this.jsonizer = jsonizer;
	}

}
