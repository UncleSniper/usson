package org.unclesniper.json.lens;

public interface DeJSONizerInto<ValueT> {

	JSONStateInto dejsonize(ValueT value, int version);

}
