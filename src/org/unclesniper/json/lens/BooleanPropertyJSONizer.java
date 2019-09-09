package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.BooleanGetter;

public class BooleanPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private BooleanGetter<? super BaseT> getter;

	public BooleanPropertyJSONizer(BooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public BooleanGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(BooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		sink.foundBoolean(getter.getBoolean(base));
	}

}
