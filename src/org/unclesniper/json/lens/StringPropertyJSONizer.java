package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectGetter;

public class StringPropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOObjectGetter<? super BaseT, String> getter;

	private IOObjectP<? super String> needed;

	public StringPropertyJSONizer(IOObjectGetter<? super BaseT, String> getter, IOObjectP<? super String> needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOObjectGetter<? super BaseT, String> getGetter() {
		return getter;
	}

	public void setGetter(IOObjectGetter<? super BaseT, String> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super String> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super String> needed) {
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
