package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectGetter;

public class StringPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String propertyName;

	private IOObjectGetter<? super BaseT, String> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOObjectP<? super String> innerNeeded;

	public StringPropertyInnerJSONizer(String propertyName, IOObjectGetter<? super BaseT, String> getter,
			IOObjectP<? super BaseT> outerNeeded, IOObjectP<? super String> innerNeeded) {
		this.propertyName = propertyName;
		this.getter = getter;
		this.outerNeeded = outerNeeded;
		this.innerNeeded = innerNeeded;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public IOObjectGetter<? super BaseT, String> getGetter() {
		return getter;
	}

	public void setGetter(IOObjectGetter<? super BaseT, String> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOObjectP<? super String> getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOObjectP<? super String> innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		String value = getter.getProperty(base);
		if(innerNeeded != null && !innerNeeded.testObject(value))
			return;
		sink.foundString(propertyName);
		sink.foundString(value);
	}

}
