package org.unclesniper.json.j8;

public interface ShortSetter<BaseT> extends IOShortSetter<BaseT> {

	@Override
	void setShort(BaseT base, short value);

}
