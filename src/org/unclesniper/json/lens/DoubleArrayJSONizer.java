package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IODoubleP;
import org.unclesniper.json.j8.IODoubleIterator;
import org.unclesniper.json.j8.IODoubleIterable;

public class DoubleArrayJSONizer<CollectionT extends IODoubleIterable> implements JSONizer<CollectionT> {

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	private IODoubleP filter;

	public DoubleArrayJSONizer(IOObjectP<? super CollectionT> needed, StaticJSON ifEmpty, IODoubleP filter) {
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

	public IODoubleP getFilter() {
		return filter;
	}

	public void setFilter(IODoubleP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IODoubleIterator it = value.doubleIterator();
		boolean first = true;
		while(it.hasNext()) {
			double element = it.nextDouble();
			if(filter != null && !filter.testDouble(element))
				continue;
			if(first) {
				sink.beginArray();
				first = false;
			}
			sink.foundFraction(element);
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
