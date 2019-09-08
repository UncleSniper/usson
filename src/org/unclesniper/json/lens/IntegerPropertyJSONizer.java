package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IntGetter;

public class IntegerPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IntGetter<? super BaseT> getter;

	private IntP needed;

	public IntegerPropertyJSONizer(IntGetter<? super BaseT> getter, IntP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IntGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IntGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IntP getNeeded() {
		return needed;
	}

	public void setNeeded(IntP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		int value = getter.getInt(base);
		if(needed == null || needed.testInt(value))
			sink.foundInteger(value);
		else
			sink.foundNull();
	}

}
