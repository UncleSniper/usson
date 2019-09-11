package org.unclesniper.json.j8;

public interface BooleanGetter<BaseT> extends IOBooleanGetter<BaseT> {

	@Override
	boolean getBoolean(BaseT base);

}
