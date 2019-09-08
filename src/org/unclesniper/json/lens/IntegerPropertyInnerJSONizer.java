package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.IntGetter;

public class IntegerPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private IntGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private IntP innerNeeded;

	public IntegerPropertyInnerJSONizer(String name, IntGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, IntP innerNeeded) {
		this.name = name;
		this.getter = getter;
		this.outerNeeded = outerNeeded;
		this.innerNeeded = innerNeeded;
	}

	public IntGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IntGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IntP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IntP innerNeeded) {
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
