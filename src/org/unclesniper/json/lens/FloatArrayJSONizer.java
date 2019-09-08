package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.FloatP;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.FloatIterable;
import org.unclesniper.json.j8.FloatIterator;

public class FloatArrayJSONizer implements JSONizer<FloatIterable> {

	private ObjectP<? super FloatIterable> needed;

	private StaticJSON ifEmpty;

	private FloatP filter;

	public FloatArrayJSONizer(ObjectP<? super FloatIterable> needed, StaticJSON ifEmpty, FloatP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super FloatIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super FloatIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public FloatP getFilter() {
		return filter;
	}

	public void setFilter(FloatP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(FloatIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		FloatIterator it = value.floatIterator();
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
