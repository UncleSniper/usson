package org.unclesniper.json.j8;

public interface ObjectP<ValueT> extends IOObjectP<ValueT> {

	@Override
	boolean testObject(ValueT value);

}
