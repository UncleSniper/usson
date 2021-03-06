package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOByteP;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOByteIterator;
import org.unclesniper.json.j8.IOByteIterable;

public class ByteArrayJSONizer<CollectionT extends IOByteIterable> implements JSONizer<CollectionT> {

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	private IOByteP filter;

	public ByteArrayJSONizer(IOObjectP<? super CollectionT> needed, StaticJSON ifEmpty, IOByteP filter) {
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

	public IOByteP getFilter() {
		return filter;
	}

	public void setFilter(IOByteP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOByteIterator it = value.byteIterator();
		boolean first = true;
		while(it.hasNext()) {
			byte element = it.nextByte();
			if(filter != null && !filter.testByte(element))
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
