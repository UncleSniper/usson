package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IODoubleP;
import org.unclesniper.json.j8.IODoubleGetter;

public class DoublePropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String propertyName;

	private IODoubleGetter<? super BaseT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IODoubleP innerNeeded;

	public DoublePropertyInnerJSONizer(String propertyName, IODoubleGetter<? super BaseT> getter,
			IOObjectP<? super BaseT> outerNeeded, IODoubleP innerNeeded) {
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

	public IODoubleGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IODoubleGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IODoubleP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IODoubleP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		double value = getter.getDouble(base);
		if(innerNeeded != null && !innerNeeded.testDouble(value))
			return;
		sink.foundString(propertyName);
		sink.foundFraction(value);
	}

}
