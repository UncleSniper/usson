package org.unclesniper.json.j8;

public interface DoubleSetter<BaseT> extends IODoubleSetter<BaseT> {

	@Override
	void setDouble(BaseT base, double value);

}
