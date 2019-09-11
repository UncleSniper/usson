package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IODoubleP;
import org.unclesniper.json.j8.IODoubleGetter;

public class DoublePropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IODoubleGetter<? super BaseT> getter;

	private IODoubleP needed;

	public DoublePropertyJSONizer(IODoubleGetter<? super BaseT> getter, IODoubleP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IODoubleGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IODoubleGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IODoubleP getNeeded() {
		return needed;
	}

	public void setNeeded(IODoubleP needed) {
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
