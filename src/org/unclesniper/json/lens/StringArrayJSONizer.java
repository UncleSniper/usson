package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOObjectIterator;
import org.unclesniper.json.j8.IOObjectIterable;

public class StringArrayJSONizer<CollectionT extends IOObjectIterable<String>> implements JSONizer<CollectionT> {

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	private IOObjectP<? super String> filter;

	public StringArrayJSONizer(IOObjectP<? super CollectionT> needed, StaticJSON ifEmpty,
			IOObjectP<? super String> filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
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

	public IOObjectP<? super String> getFilter() {
		return filter;
	}

	public void setFilter(IOObjectP<? super String> filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		boolean first = true;
		IOObjectIterator<String> it = value.objectIterator();
		while(it.hasNext()) {
			String element = it.next();
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
