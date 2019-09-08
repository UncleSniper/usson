package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.FloatP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.FloatGetter;

public class FloatPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private FloatGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> outerNeeded;

	private FloatP innerNeeded;

	public FloatPropertyInnerJSONizer(String name, FloatGetter<? super BaseT> getter,
			ObjectP<? super BaseT> outerNeeded, FloatP innerNeeded) {
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

	public FloatGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(FloatGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(ObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public FloatP getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(FloatP innerNeeded) {
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
