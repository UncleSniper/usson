package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOLongP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOLongGetter;

public class LongPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOLongGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOLongP innerNeeded;

	public LongPropertyInnerJSONizer(String name, IOLongGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOLongP innerNeeded) {
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

	public IOLongGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOLongGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOLongP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOLongP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		long value = getter.getLong(base);
		if(innerNeeded != null && !innerNeeded.testLong(value))
			return;
		sink.foundString(name);
		sink.foundInteger(value);
	}

}
