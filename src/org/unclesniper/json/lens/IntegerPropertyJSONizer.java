package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOIntP;
import org.unclesniper.json.j8.IOIntGetter;

public class IntegerPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOIntGetter<? super BaseT> getter;

	private IOIntP needed;

	public IntegerPropertyJSONizer(IOIntGetter<? super BaseT> getter, IOIntP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOIntGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOIntGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOIntP getNeeded() {
		return needed;
	}

	public void setNeeded(IOIntP needed) {
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
