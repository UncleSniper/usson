package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOLongP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOLongIterator;
import org.unclesniper.json.j8.IOLongIterable;

public class LongArrayJSONizer implements JSONizer<IOLongIterable> {

	private IOObjectP<? super IOLongIterable> needed;

	private StaticJSON ifEmpty;

	private IOLongP filter;

	public LongArrayJSONizer(IOObjectP<? super IOLongIterable> needed, StaticJSON ifEmpty, IOLongP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public IOObjectP<? super IOLongIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super IOLongIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public IOLongP getFilter() {
		return filter;
	}

	public void setFilter(IOLongP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(IOLongIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOLongIterator it = value.longIterator();
		boolean first = true;
		while(it.hasNext()) {
			long element = it.nextLong();
			if(filter != null && !filter.testLong(element))
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
