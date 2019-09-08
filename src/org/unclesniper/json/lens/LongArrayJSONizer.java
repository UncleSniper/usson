package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.LongP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.LongIterator;
import org.unclesniper.json.j8.LongIterable;

public class LongArrayJSONizer implements JSONizer<LongIterable> {

	private ObjectP<? super LongIterable> needed;

	private StaticJSON ifEmpty;

	private LongP filter;

	public LongArrayJSONizer(ObjectP<? super LongIterable> needed, StaticJSON ifEmpty, LongP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super LongIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super LongIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public LongP getFilter() {
		return filter;
	}

	public void setFilter(LongP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(LongIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		LongIterator it = value.longIterator();
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
