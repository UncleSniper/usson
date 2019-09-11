package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IOIntP;

public abstract class VersionBoundJSONizerBase<ValueT> {

	protected IOIntP predicate;

	protected InnerJSONizer<? super ValueT> jsonizer;

	public VersionBoundJSONizerBase(IOIntP predicate, InnerJSONizer<? super ValueT> jsonizer) {
		this.predicate = predicate;
		this.jsonizer = jsonizer;
	}

	public IOIntP getPredicate() {
		return predicate;
	}

	public void setPredicate(IOIntP predicate) {
		this.predicate = predicate;
	}

	public InnerJSONizer<? super ValueT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(InnerJSONizer<? super ValueT> jsonizer) {
		this.jsonizer = jsonizer;
	}

}
