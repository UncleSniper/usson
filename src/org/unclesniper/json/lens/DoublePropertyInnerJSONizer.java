package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.DoubleP;
import org.unclesniper.json.j8.DoubleGetter;

public class DoublePropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private DoubleGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private DoubleP innerNeeded;

	public DoublePropertyInnerJSONizer(String name, DoubleGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, DoubleP innerNeeded) {
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

	public DoubleGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(DoubleGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public DoubleP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(DoubleP innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		double value = getter.getDouble(base);
		if(innerNeeded != null && !innerNeeded.testDouble(value))
			return;
		sink.foundString(name);
		sink.foundFraction(value);
	}

}
