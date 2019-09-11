package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOFloatP;
import org.unclesniper.json.j8.IOFloatGetter;

public class FloatPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOFloatGetter<? super BaseT> getter;

	private IOFloatP needed;

	public FloatPropertyJSONizer(IOFloatGetter<? super BaseT> getter, IOFloatP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOFloatGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOFloatGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOFloatP getNeeded() {
		return needed;
	}

	public void setNeeded(IOFloatP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		float value = getter.getFloat(base);
		if(needed == null || needed.testFloat(value))
			sink.foundFraction(value);
		else
			sink.foundNull();
	}

}
