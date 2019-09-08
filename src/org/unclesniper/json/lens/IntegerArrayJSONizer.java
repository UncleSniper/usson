package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IntP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.IntIterator;
import org.unclesniper.json.j8.IntIterable;

public class IntegerArrayJSONizer implements JSONizer<IntIterable> {

	private ObjectP<? super IntIterable> needed;

	private StaticJSON ifEmpty;

	private IntP filter;

	public IntegerArrayJSONizer(ObjectP<? super IntIterable> needed, StaticJSON ifEmpty, IntP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super IntIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super IntIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public IntP getFilter() {
		return filter;
	}

	public void setFilter(IntP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(IntIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IntIterator it = value.intIterator();
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
