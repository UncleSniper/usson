package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ShortP;
import org.unclesniper.json.j8.ShortGetter;

public class ShortPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private ShortGetter<? super BaseT> getter;

	private ShortP needed;

	public ShortPropertyJSONizer(ShortGetter<? super BaseT> getter, ShortP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public ShortGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(ShortGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ShortP getNeeded() {
		return needed;
	}

	public void setNeeded(ShortP needed) {
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
