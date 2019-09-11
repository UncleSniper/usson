package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOIntP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOIntIterator;
import org.unclesniper.json.j8.IOIntIterable;

public class IntegerArrayJSONizer implements JSONizer<IOIntIterable> {

	private IOObjectP<? super IOIntIterable> needed;

	private StaticJSON ifEmpty;

	private IOIntP filter;

	public IntegerArrayJSONizer(IOObjectP<? super IOIntIterable> needed, StaticJSON ifEmpty, IOIntP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public IOObjectP<? super IOIntIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super IOIntIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public IOIntP getFilter() {
		return filter;
	}

	public void setFilter(IOIntP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(IOIntIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOIntIterator it = value.intIterator();
		boolean first = true;
		while(it.hasNext()) {
			int element = it.nextInt();
			if(filter != null && !filter.testInt(element))
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
