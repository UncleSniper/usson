package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOFloatP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOFloatGetter;

public class FloatPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOFloatGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOFloatP innerNeeded;

	public FloatPropertyInnerJSONizer(String name, IOFloatGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOFloatP innerNeeded) {
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

	public IOFloatGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOFloatGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOFloatP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOFloatP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		float value = getter.getFloat(base);
		if(innerNeeded != null && !innerNeeded.testFloat(value))
			return;
		sink.foundString(name);
		sink.foundFraction(value);
	}

}
