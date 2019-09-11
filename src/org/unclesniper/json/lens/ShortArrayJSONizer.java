package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOShortP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOShortIterator;
import org.unclesniper.json.j8.IOShortIterable;

public class ShortArrayJSONizer implements JSONizer<IOShortIterable> {

	private IOObjectP<? super IOShortIterable> needed;

	private StaticJSON ifEmpty;

	private IOShortP filter;

	public ShortArrayJSONizer(IOObjectP<? super IOShortIterable> needed, StaticJSON ifEmpty, IOShortP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public IOObjectP<? super IOShortIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super IOShortIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public IOShortP getFilter() {
		return filter;
	}

	public void setFilter(IOShortP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(IOShortIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOShortIterator it = value.shortIterator();
		boolean first = true;
		while(it.hasNext()) {
			short element = it.nextShort();
			if(filter != null && !filter.testShort(element))
				continue;
			if(first) {
				sink.beginArray();
				first = false;
			}
			sink.foundInteger(element);
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
