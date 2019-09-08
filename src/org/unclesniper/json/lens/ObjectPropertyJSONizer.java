package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ObjectGetter;

public class ObjectPropertyJSONizer<BaseT, PropertyT> implements JSONizer<BaseT> {

	private ObjectGetter<? super BaseT, ? extends PropertyT> getter;

	private ObjectP<? super BaseT> needed;

	private JSONizer<? super PropertyT> jsonizer;

	public ObjectPropertyJSONizer(ObjectGetter<? super BaseT, ? extends PropertyT> getter,
			ObjectP<? super BaseT> needed, JSONizer<? super PropertyT> jsonizer) {
		this.getter = getter;
		this.needed = needed;
		this.jsonizer = jsonizer;
	}

	public ObjectGetter<? super BaseT, ? extends PropertyT> getGetter() {
		return getter;
	}

	public void setGetter(ObjectGetter<? super BaseT, ? extends PropertyT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super BaseT> needed) {
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
