package org.unclesniper.json.lens;

public interface DeJSONizer<ValueT> {

	JSONState<ValueT> dejsonize(int version);

}
