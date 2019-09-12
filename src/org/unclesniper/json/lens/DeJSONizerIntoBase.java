package org.unclesniper.json.lens;

public interface DeJSONizerIntoBase<ValueT> {

	JSONState dejsonize(ValueT value, int version, JSONState parent);

}
