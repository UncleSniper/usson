package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.ByteP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ByteGetter;

public class BytePropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private ByteGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private ByteP innerNeeded;

	public BytePropertyInnerJSONizer(String name, ByteGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, ByteP innerNeeded) {
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

	public ByteGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(ByteGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public ByteP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(ByteP innerNeeded) {
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
