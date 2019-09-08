package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.LongP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.LongGetter;

public class LongPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private LongGetter<? super BaseT> getter;

	private LongP needed;

	public LongPropertyJSONizer(LongGetter<? super BaseT> getter, LongP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public LongGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(LongGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public LongP getNeeded() {
		return needed;
	}

	public void setNeeded(LongP needed) {
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
