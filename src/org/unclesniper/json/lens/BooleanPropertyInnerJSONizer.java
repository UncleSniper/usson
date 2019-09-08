package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.BooleanGetter;

public class BooleanPropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private BooleanGetter<? super BaseT> getter;

	private ObjectP<? super BaseT> needed;

	public BooleanPropertyInnerJSONizer(String name, BooleanGetter<? super BaseT> getter,
			ObjectP<? super BaseT> needed) {
		this.name = name;
		this.getter = getter;
		this.needed = needed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BooleanGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(BooleanGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super BaseT> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(base) : base == null)
			return;
		sink.foundString(name);
		sink.foundBoolean(getter.getBoolean(base));
	}

}
