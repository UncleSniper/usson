package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectIterable;
import org.unclesniper.json.j8.IOObjectIterator;
import org.unclesniper.json.j8.ArrayObjectIterable;

public class ObjectJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOObjectIterable<? extends InnerJSONizer<? super BaseT>> properties;

	private IOObjectP<? super BaseT> needed;

	public ObjectJSONizer(IOObjectIterable<? extends InnerJSONizer<? super BaseT>> properties) {
		this(properties, null);
	}

	public ObjectJSONizer(IOObjectIterable<? extends InnerJSONizer<? super BaseT>> properties,
			IOObjectP<? super BaseT> needed) {
		this.properties = properties;
		this.needed = needed;
	}

	public ObjectJSONizer(InnerJSONizer<? super BaseT>... properties) {
		this(null, properties);
	}

	public ObjectJSONizer(IOObjectP<? super BaseT> needed, InnerJSONizer<? super BaseT>... properties) {
		setProperties(properties);
		this.needed = needed;
	}

	public IOObjectIterable<? extends InnerJSONizer<? super BaseT>> getProperties() {
		return properties;
	}

	public void setProperties(IOObjectIterable<? extends InnerJSONizer<? super BaseT>> properties) {
		this.properties = properties;
	}

	public void setProperties(InnerJSONizer<? super BaseT>[] properties) {
		this.properties = properties == null || properties.length == 0
				? null : new ArrayObjectIterable<InnerJSONizer<? super BaseT>>(properties);
	}

	public IOObjectP<? super BaseT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super BaseT> needed) {
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
			IOObjectIterator<? extends InnerJSONizer<? super BaseT>> it = properties.objectIterator();
			while(it.hasNext())
				it.next().jsonize(base, sink, version);
		}
		sink.endObject();
	}

}
