package org.unclesniper.json.j8;

public interface DoubleGetter<BaseT> extends IODoubleGetter<BaseT> {

	@Override
	double getDouble(BaseT base);

}
