package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class StringArrayJSONizer implements JSONizer<Iterable<String>> {

	private ObjectP<? super Iterable<String>> needed;

	private StaticJSON ifEmpty;

	private ObjectP<? super String> filter;

	public StringArrayJSONizer(ObjectP<? super Iterable<String>> needed, StaticJSON ifEmpty,
			ObjectP<? super String> filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super Iterable<String>> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Iterable<String>> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public ObjectP<? super String> getFilter() {
		return filter;
	}

	public void setFilter(ObjectP<? super String> filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(Iterable<String> value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		boolean first = true;
		for(String element : value) {
			if(filter != null && !filter.testObject(element))
				continue;
			if(first) {
				sink.beginArray();
				first = false;
			}
			sink.foundString(element);
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
