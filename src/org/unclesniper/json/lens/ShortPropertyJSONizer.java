package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOShortP;
import org.unclesniper.json.j8.IOShortGetter;

public class ShortPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOShortGetter<? super BaseT> getter;

	private IOShortP needed;

	public ShortPropertyJSONizer(IOShortGetter<? super BaseT> getter, IOShortP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOShortGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOShortGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOShortP getNeeded() {
		return needed;
	}

	public void setNeeded(IOShortP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		short value = getter.getShort(base);
		if(needed == null || needed.testShort(value))
			sink.foundInteger(value);
		else
			sink.foundNull();
	}

}
