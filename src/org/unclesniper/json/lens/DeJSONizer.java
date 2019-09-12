package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IOObjectSink;

public interface DeJSONizer<ValueT> {

	JSONState dejsonize(IOObjectSink<? super ValueT> sink, int version, JSONState parent);

}
