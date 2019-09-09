package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ObjectGetter;

public class StringPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private ObjectGetter<? super BaseT, String> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private ObjectP<? super String> innerNeeded;

	public StringPropertyInnerJSONizer(String name, ObjectGetter<? super BaseT, String> getter,
			ObjectP<? super BaseT> outerNeeded, ObjectP<? super String> innerNeeded) {
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

	public ObjectGetter<? super BaseT, String> getGetter() {
		return getter;
	}

	public void setGetter(ObjectGetter<? super BaseT, String> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public ObjectP<? super String> getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(ObjectP<? super String> innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		String value = getter.getProperty(base);
		if(innerNeeded != null && !innerNeeded.testObject(value))
			return;
		sink.foundString(name);
		sink.foundString(value);
	}

}
