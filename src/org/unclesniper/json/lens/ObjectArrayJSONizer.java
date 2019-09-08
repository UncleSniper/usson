package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class ObjectArrayJSONizer<ElementT> implements JSONizer<Iterable<? extends ElementT>> {

	private JSONizer<? super ElementT> jsonizer;

	private ObjectP<? super Iterable<? extends ElementT>> needed;

	private StaticJSON ifEmpty;

	private ObjectP<? super ElementT> filter;

	public ObjectArrayJSONizer(JSONizer<? super ElementT> jsonizer,
			ObjectP<? super Iterable<? extends ElementT>> needed, StaticJSON ifEmpty,
			ObjectP<? super ElementT> filter) {
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

	public ObjectP<? super Iterable<? extends ElementT>> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Iterable<? extends ElementT>> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public ObjectP<? super ElementT> getFilter() {
		return filter;
	}

	public void setFilter(ObjectP<? super ElementT> filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(Iterable<? extends ElementT> value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		boolean first = true;
		for(ElementT element : value) {
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
