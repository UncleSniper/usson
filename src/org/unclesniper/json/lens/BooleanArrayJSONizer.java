package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;
import org.unclesniper.json.j8.IOBooleanIterable;
import org.unclesniper.json.j8.IOBooleanIterator;

public class BooleanArrayJSONizer<CollectionT extends IOBooleanIterable> implements JSONizer<CollectionT> {

	private IOObjectP<? super CollectionT> needed;

	private StaticJSON ifEmpty;

	public BooleanArrayJSONizer(IOObjectP<? super CollectionT> needed, StaticJSON ifEmpty) {
		this.needed = needed;
		this.ifEmpty = ifEmpty;
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

	@Override
	public void jsonize(CollectionT value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value))) {
			sink.foundNull();
			return;
		}
		IOBooleanIterator it = value.booleanIterator();
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
