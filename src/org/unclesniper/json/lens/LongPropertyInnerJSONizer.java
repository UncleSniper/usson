package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.LongP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.LongGetter;

public class LongPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private LongGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private LongP innerNeeded;

	public LongPropertyInnerJSONizer(String name, LongGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, LongP innerNeeded) {
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

	public LongGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(LongGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public LongP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(LongP innerNeeded) {
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
