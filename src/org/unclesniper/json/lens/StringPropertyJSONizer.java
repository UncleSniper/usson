package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ObjectGetter;

public class StringPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private ObjectGetter<? super BaseT, String> getter;

	private ObjectP<? super String> needed;

	public StringPropertyJSONizer(ObjectGetter<? super BaseT, String> getter, ObjectP<? super String> needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public ObjectGetter<? super BaseT, String> getGetter() {
		return getter;
	}

	public void setGetter(ObjectGetter<? super BaseT, String> getter) {
		this.getter = getter;
	}

	public ObjectP<? super String> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super String> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		String value = getter.getProperty(base);
		if(value != null && (needed == null || needed.testObject(value)))
			sink.foundString(value);
		else
			sink.foundNull();
	}

}
