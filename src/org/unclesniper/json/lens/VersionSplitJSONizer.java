package org.unclesniper.json.lens;

public class VersionSplitJSONizer<ValueT> extends VersionSplitJSONizerBase<ValueT> implements JSONizer<ValueT> {

	public VersionSplitJSONizer(int version, InnerJSONizer<? super ValueT> lower,
			InnerJSONizer<? super ValueT> atLeast) {
		super(version, lower, atLeast);
	}

}
