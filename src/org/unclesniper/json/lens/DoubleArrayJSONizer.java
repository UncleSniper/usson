package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.DoubleP;
import org.unclesniper.json.j8.DoubleIterator;
import org.unclesniper.json.j8.DoubleIterable;

public class DoubleArrayJSONizer implements JSONizer<DoubleIterable> {

	private ObjectP<? super DoubleIterable> needed;

	private StaticJSON ifEmpty;

	private DoubleP filter;

	public DoubleArrayJSONizer(ObjectP<? super DoubleIterable> needed, StaticJSON ifEmpty, DoubleP filter) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
		this.filter = filter;
	}

	public ObjectP<? super DoubleIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super DoubleIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	public DoubleP getFilter() {
		return filter;
	}

	public void setFilter(DoubleP filter) {
		this.filter = filter;
	}

	@Override
	public void jsonize(DoubleIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		DoubleIterator it = value.doubleIterator();
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
