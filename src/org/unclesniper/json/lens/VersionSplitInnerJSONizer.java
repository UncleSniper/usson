package org.unclesniper.json.lens;

public class VersionSplitInnerJSONizer<ValueT>
		extends VersionSplitJSONizerBase<ValueT> implements InnerJSONizer<ValueT> {

	public VersionSplitInnerJSONizer(int version, InnerJSONizer<? super ValueT> lower,
			InnerJSONizer<? super ValueT> atLeast) {
		super(version, lower, atLeast);
	}

}
