package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ShortP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ShortGetter;

public class ShortPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private ShortGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private ShortP innerNeeded;

	public ShortPropertyInnerJSONizer(String name, ShortGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, ShortP innerNeeded) {
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

	public ShortGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(ShortGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public ShortP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(ShortP innerNeeded) {
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
