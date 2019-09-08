package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ArrayIterable;

public class ObjectJSONizer<BaseT> implements JSONizer<BaseT> {

	private Iterable<? extends InnerJSONizer<? super BaseT>> properties;

	private ObjectP<? super BaseT> needed;

	public ObjectJSONizer(Iterable<? extends InnerJSONizer<? super BaseT>> properties) {
		this(properties, null);
	}

	public ObjectJSONizer(Iterable<? extends InnerJSONizer<? super BaseT>> properties,
			ObjectP<? super BaseT> needed) {
		this.properties = properties;
		this.needed = needed;
	}

	public ObjectJSONizer(InnerJSONizer<? super BaseT>... properties) {
		this(null, properties);
	}

	public ObjectJSONizer(ObjectP<? super BaseT> needed, InnerJSONizer<? super BaseT>... properties) {
		setProperties(properties);
		this.needed = needed;
	}

	public Iterable<? extends InnerJSONizer<? super BaseT>> getProperties() {
		return properties;
	}

	public void setProperties(Iterable<? extends InnerJSONizer<? super BaseT>> properties) {
		this.properties = properties;
	}

	public void setProperties(InnerJSONizer<? super BaseT>[] properties) {
		this.properties = properties == null || properties.length == 0
				? null : new ArrayIterable<InnerJSONizer<? super BaseT>>(properties);
	}

	public ObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super BaseT> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(base) : base == null) {
			sink.foundNull();
			return;
		}
		sink.beginObject();
		if(properties != null) {
			for(InnerJSONizer<? super BaseT> property : properties)
				property.jsonize(base, sink, version);
		}
		sink.endObject();
	}

}
