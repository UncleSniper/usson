package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;

public abstract class VersionSplitJSONizerBase<ValueT> implements JSONizerBase<ValueT> {

	private int version;

	private InnerJSONizer<? super ValueT> lower;

	private InnerJSONizer<? super ValueT> atLeast;

	public VersionSplitJSONizerBase(int version, InnerJSONizer<? super ValueT> lower,
			InnerJSONizer<? super ValueT> atLeast) {
		this.version = version;
		this.lower = lower;
		this.atLeast = atLeast;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public InnerJSONizer<? super ValueT> getLower() {
		return lower;
	}

	public void setLower(InnerJSONizer<? super ValueT> lower) {
		this.lower = lower;
	}

	public InnerJSONizer<? super ValueT> getAtLeast() {
		return atLeast;
	}

	public void setAtLeast(InnerJSONizer<? super ValueT> atLeast) {
		this.atLeast = atLeast;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(version < this.version)
			lower.jsonize(value, sink, version);
		else
			atLeast.jsonize(value, sink, version);
	}

}
