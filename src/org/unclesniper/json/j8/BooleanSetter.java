package org.unclesniper.json.j8;

public interface BooleanSetter<BaseT> extends IOBooleanSetter<BaseT> {

	@Override
	void setBoolean(BaseT base, boolean value);

}
