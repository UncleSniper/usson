package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOByteP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOByteGetter;

public class BytePropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOByteGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOByteP innerNeeded;

	public BytePropertyInnerJSONizer(String name, IOByteGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOByteP innerNeeded) {
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

	public IOByteGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOByteGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOByteP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOByteP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		byte value = getter.getByte(base);
		if(innerNeeded != null && !innerNeeded.testByte(value))
			return;
		sink.foundString(name);
		sink.foundInteger(value);
	}

}
