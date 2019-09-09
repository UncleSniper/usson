package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.FloatP;
import org.unclesniper.json.j8.FloatGetter;

public class FloatPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private FloatGetter<? super BaseT> getter;

	private FloatP needed;

	public FloatPropertyJSONizer(FloatGetter<? super BaseT> getter, FloatP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public FloatGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(FloatGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public FloatP getNeeded() {
		return needed;
	}

	public void setNeeded(FloatP needed) {
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
