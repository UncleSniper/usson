package org.unclesniper.json.j8;

public interface IntSetter<BaseT> extends IOIntSetter<BaseT> {

	@Override
	void setInt(BaseT base, int value);

}
