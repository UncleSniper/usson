package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOLongP;
import org.unclesniper.json.j8.IOLongGetter;

public class LongPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOLongGetter<? super BaseT> getter;

	private IOLongP needed;

	public LongPropertyJSONizer(IOLongGetter<? super BaseT> getter, IOLongP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOLongGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOLongGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOLongP getNeeded() {
		return needed;
	}

	public void setNeeded(IOLongP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		long value = getter.getLong(base);
		if(needed == null || needed.testLong(value))
			sink.foundInteger(value);
		else
			sink.foundNull();
	}

}
