package org.unclesniper.json.lens;

public interface JSONState<ValueT> extends JSONStateBase<JSONState<ValueT>> {

	ValueT getStateValue();

}
