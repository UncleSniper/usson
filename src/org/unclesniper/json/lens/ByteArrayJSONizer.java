package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.ByteP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ByteIterator;
import org.unclesniper.json.j8.ByteIterable;

public class ByteArrayJSONizer implements JSONizer<ByteIterable> {

	private ObjectP<? super ByteIterable> needed;

	private StaticJSON ifEmpty;

	private ByteP filter;

	public ByteArrayJSONizer(ObjectP<? super ByteIterable> needed, StaticJSON ifEmpty, ByteP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super ByteIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super ByteIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public ByteP getFilter() {
		return filter;
	}

	public void setFilter(ByteP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(ByteIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		ByteIterator it = value.byteIterator();
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
