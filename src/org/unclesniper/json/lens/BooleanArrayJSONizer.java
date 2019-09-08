package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;
import org.unclesniper.json.j8.BooleanIterable;
import org.unclesniper.json.j8.BooleanIterator;

public class BooleanArrayJSONizer implements JSONizer<BooleanIterable> {

	private ObjectP<? super BooleanIterable> needed;

	private StaticJSON ifEmpty;

	public BooleanArrayJSONizer(ObjectP<? super BooleanIterable> needed, StaticJSON ifEmpty) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
	}

	public ObjectP<? super BooleanIterable> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super BooleanIterable> needed) {
		this.needed = needed;
	}

	public StaticJSON getIfEmpty() {
		return ifEmpty;
	}

	public void setIfEmpty(StaticJSON ifEmpty) {
		this.ifEmpty = ifEmpty;
	}

	@Override
	public void jsonize(BooleanIterable value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		BooleanIterator it = value.booleanIterator();
		boolean first = true;
		while(it.hasNext()) {
			if(first) {
				sink.beginArray();
				first = false;
			}
			sink.foundBoolean(it.next());
		}
		if(first) {
			if(ifEmpty != null)
				ifEmpty.jsonize(sink, version);
		}
		else
			sink.endArray();
	}

}
