package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOBooleanGetter;

public class BooleanPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String propertyName;

	private IOBooleanGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> needed;

	public BooleanPropertyInnerJSONizer(String propertyName, IOBooleanGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> needed) {
		this.propertyName = propertyName;
		this.getter = getter;
		this.needed = needed;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public IOBooleanGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOBooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super BaseT> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(base) : base == null)
			return;
		sink.foundString(propertyName);
		sink.foundBoolean(getter.getBoolean(base));
	}

}
