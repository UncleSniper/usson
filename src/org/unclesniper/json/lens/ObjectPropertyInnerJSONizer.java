package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectGetter;

public class ObjectPropertyInnerJSONizer<BaseT, PropertyT> implements InnerJSONizer<BaseT> {

	private String name;

	private IOObjectGetter<? super BaseT, ? extends PropertyT> getter;

	private IOObjectP<? super BaseT> outerNeeded;

	private IOObjectP<? super PropertyT> innerNeeded;

	private JSONizer<? super PropertyT> jsonizer;

	public ObjectPropertyInnerJSONizer(String name, IOObjectGetter<? super BaseT, ? extends PropertyT> getter,
			IOObjectP<? super BaseT> outerNeeded, IOObjectP<? super PropertyT> innerNeeded,
			JSONizer<? super PropertyT> jsonizer) {
		this.name = name;
		this.getter = getter;
		this.outerNeeded = outerNeeded;
		this.innerNeeded = innerNeeded;
		this.jsonizer = jsonizer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IOObjectGetter<? super BaseT, ? extends PropertyT> getGetter() {
		return getter;
	}

	public void setGetter(IOObjectGetter<? super BaseT, ? extends PropertyT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getOuterNeeded() {
		return outerNeeded;
	}

	public void setOuterNeeded(IOObjectP<? super BaseT> outerNeeded) {
		this.outerNeeded = outerNeeded;
	}

	public IOObjectP<? super PropertyT> getInnerNeeded() {
		return innerNeeded;
	}

	public void setInnerNeeded(IOObjectP<? super PropertyT> innerNeeded) {
		this.innerNeeded = innerNeeded;
	}

	public JSONizer<? super PropertyT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(JSONizer<? super PropertyT> jsonizer) {
		this.jsonizer = jsonizer;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(outerNeeded != null ? !outerNeeded.testObject(base) : base == null)
			return;
		PropertyT value = getter.getProperty(base);
		if(innerNeeded != null && !innerNeeded.testObject(value))
			return;
		sink.foundString(name);
		jsonizer.jsonize(value, sink, version);
	}

}
