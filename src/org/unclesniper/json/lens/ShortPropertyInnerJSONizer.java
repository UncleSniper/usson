package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOShortP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOShortGetter;

public class ShortPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOShortGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOShortP innerNeeded;

	public ShortPropertyInnerJSONizer(String name, IOShortGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOShortP innerNeeded) {
		this.name = name;
		this.getter = getter;
		this.outerNeeded = outerNeeded;
		this.innerNeeded = innerNeeded;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IOShortGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOShortGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOShortP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOShortP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		short value = getter.getShort(base);
		if(innerNeeded != null && !innerNeeded.testShort(value))
			return;
		sink.foundString(name);
		sink.foundInteger(value);
	}

}
