package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.BooleanP;
import org.unclesniper.json.j8.BooleanGetter;

public class BooleanPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private BooleanGetter<? super BaseT> getter;

	private BooleanP needed;

	public BooleanPropertyJSONizer(BooleanGetter<? super BaseT> getter, BooleanP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public BooleanGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(BooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public BooleanP getNeeded() {
		return needed;
	}

	public void setNeeded(BooleanP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		boolean value = getter.getBoolean(base);
		if(needed == null || needed.testBoolean(value))
			sink.foundBoolean(value);
		else
			sink.foundNull();
	}

}
