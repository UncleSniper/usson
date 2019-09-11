package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectGetter;

public class ObjectPropertyJSONizer<BaseT, PropertyT> implements JSONizer<BaseT> {

	private IOObjectGetter<? super BaseT, ? extends PropertyT> getter;

	private IOObjectP<? super BaseT> needed;

	private JSONizer<? super PropertyT> jsonizer;

	public ObjectPropertyJSONizer(IOObjectGetter<? super BaseT, ? extends PropertyT> getter,
			IOObjectP<? super BaseT> needed, JSONizer<? super PropertyT> jsonizer) {
		this.getter = getter;
		this.needed = needed;
		this.jsonizer = jsonizer;
	}

	public IOObjectGetter<? super BaseT, ? extends PropertyT> getGetter() {
		return getter;
	}

	public void setGetter(IOObjectGetter<? super BaseT, ? extends PropertyT> getter) {
		this.getter = getter;
	}

	public IOObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super BaseT> needed) {
		this.needed = needed;
	}

	public JSONizer<? super PropertyT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(JSONizer<? super PropertyT> jsonizer) {
		this.jsonizer = jsonizer;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(base) : base == null)
			sink.foundNull();
		else
			jsonizer.jsonize(getter.getProperty(base), sink, version);
	}

}
