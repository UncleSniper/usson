package org.unclesniper.json.j8;

public interface LongSetter<BaseT> extends IOLongSetter<BaseT> {

	@Override
	void setLong(BaseT base, long value);

}
