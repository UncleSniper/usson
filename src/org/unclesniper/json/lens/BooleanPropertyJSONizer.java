package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOBooleanGetter;

public class BooleanPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOBooleanGetter<? super BaseT> getter;

	public BooleanPropertyJSONizer(IOBooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOBooleanGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOBooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		sink.foundBoolean(getter.getBoolean(base));
	}

}
