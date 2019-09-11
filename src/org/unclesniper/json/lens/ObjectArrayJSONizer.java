package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectIterable;
import org.unclesniper.json.j8.IOObjectIterator;

public class ObjectArrayJSONizer<ElementT, CollectionT extends IOObjectIterable<? extends ElementT>>
		implements JSONizer<CollectionT> {

	private JSONizer<? super ElementT> jsonizer;

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	private IOObjectP<? super ElementT> filter;

	public ObjectArrayJSONizer(JSONizer<? super ElementT> jsonizer, IOObjectP<? super CollectionT> needed,
			StaticJSON ifEmpty, IOObjectP<? super ElementT> filter) {
		this.jsonizer = jsonizer;
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public JSONizer<? super ElementT> getJSONizer() {
		return jsonizer;
	}

	public void setJSONizer(JSONizer<? super ElementT> jsonizer) {
		this.jsonizer = jsonizer;
	}

	public IOObjectP<? super CollectionT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super CollectionT> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public IOObjectP<? super ElementT> getFilter() {
		return filter;
	}

	public void setFilter(IOObjectP<? super ElementT> filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		boolean first = true;
		IOObjectIterator<? extends ElementT> it = value.objectIterator();
		while(it.hasNext()) {
			ElementT element = it.next();
			if(filter != null && !filter.testObject(element))
				continue;
			if(first) {
				sink.beginArray();
				first = false;
			}
			jsonizer.jsonize(element, sink, version);
		}
		if(first) {
			if(ifEmpty != null)
				ifEmpty.jsonize(sink, version);
			else {
				sink.beginArray();
				sink.endArray();
			}
		}
		else
			sink.endArray();
	}

}
