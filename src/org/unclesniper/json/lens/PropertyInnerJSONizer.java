package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class PropertyInnerJSONizer<BaseT> implements InnerJSONizer<BaseT> {

	private String propertyName;

	private JSONizer<? super BaseT> property;

	private IOObjectP<? super BaseT> needed;

	public PropertyInnerJSONizer(String propertyName, JSONizer<? super BaseT> property) {
		this(propertyName, property, null);
	}

	public PropertyInnerJSONizer(String propertyName, JSONizer<? super BaseT> property,
			IOObjectP<? super BaseT> needed) {
		this.propertyName = propertyName;
		this.property = property;
		this.needed = needed;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public JSONizer<? super BaseT> getProperty() {
		return property;
	}

	public void setProperty(JSONizer<? super BaseT> property) {
		this.property = property;
	}

	public IOObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super BaseT> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(base) : base == null)
			return;
		sink.foundString(propertyName);
		property.jsonize(base, sink, version);
	}

}
