package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOFloatP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOFloatIterable;
import org.unclesniper.json.j8.IOFloatIterator;

public class FloatArrayJSONizer<CollectionT extends IOFloatIterable> implements JSONizer<CollectionT> {

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	private IOFloatP filter;

	public FloatArrayJSONizer(IOObjectP<? super CollectionT> needed, StaticJSON ifEmpty, IOFloatP filter) {
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

	public IOFloatP getFilter() {
		return filter;
	}

	public void setFilter(IOFloatP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOFloatIterator it = value.floatIterator();
		boolean first = true;
		while(it.hasNext()) {
			float element = it.nextFloat();
			if(filter != null && !filter.testFloat(element))
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
