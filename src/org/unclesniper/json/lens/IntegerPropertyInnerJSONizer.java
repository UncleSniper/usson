package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOIntP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOIntGetter;

public class IntegerPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOIntGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOIntP innerNeeded;

	public IntegerPropertyInnerJSONizer(String name, IOIntGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOIntP innerNeeded) {
		this.name = name;
		this.getter = getter;
		this.outerNeeded = outerNeeded;
		this.innerNeeded = innerNeeded;
	}

	public IOIntGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOIntGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOIntP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOIntP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		int value = getter.getInt(base);
		if(innerNeeded != null && !innerNeeded.testInt(value))
			return;
		sink.foundString(name);
		sink.foundInteger(value);
	}

}
