package org.unclesniper.json.j8;

public interface ObjectSink<ValueT> extends IOObjectSink<ValueT> {

	@Override
	void putObject(ValueT value);

}
