package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ShortP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.ShortIterator;
import org.unclesniper.json.j8.ShortIterable;

public class ShortArrayJSONizer implements JSONizer<ShortIterable> {

	private ObjectP<? super ShortIterable> needed;

	private StaticJSON ifEmpty;

	private ShortP filter;

	public ShortArrayJSONizer(ObjectP<? super ShortIterable> needed, StaticJSON ifEmpty, ShortP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super ShortIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super ShortIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public ShortP getFilter() {
		return filter;
	}

	public void setFilter(ShortP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(ShortIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		ShortIterator it = value.shortIterator();
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
