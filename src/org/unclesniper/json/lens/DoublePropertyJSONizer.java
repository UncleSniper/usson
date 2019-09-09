package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.DoubleP;
import org.unclesniper.json.j8.DoubleGetter;

public class DoublePropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private DoubleGetter<? super BaseT> getter;

	private DoubleP needed;

	public DoublePropertyJSONizer(DoubleGetter<? super BaseT> getter, DoubleP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public DoubleGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(DoubleGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public DoubleP getNeeded() {
		return needed;
	}

	public void setNeeded(DoubleP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		double value = getter.getDouble(base);
		if(needed == null || needed.testDouble(value))
			sink.foundFraction(value);
		else
			sink.foundNull();
	}

}
