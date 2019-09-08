package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class PropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String name;

	private JSONizer<? super BaseT> property;

	private ObjectP<? super BaseT> needed;

	public PropertyInnerJSONizer(String name, JSONizer<? super BaseT> property) {
		this(name, property, null);
	}

	public PropertyInnerJSONizer(String name, JSONizer<? super BaseT> property, ObjectP<? super BaseT> needed) {
		this.name = name;
		this.property = property;
		this.needed = needed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONizer<? super BaseT> getProperty() {
		return property;
	}

	public void setProperty(JSONizer<? super BaseT> property) {
		this.property = property;
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
		property.jsonize(base, sink, version);
	}

}
