package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class ArrayJSONizer<ValueT> implements JSONizer<ValueT> {

	private SequenceJSONizer<? super ValueT> generator;

	private IOObjectP<? super ValueT> needed;

	public ArrayJSONizer(SequenceJSONizer<? super ValueT> generator, IOObjectP<? super ValueT> needed) {
		this.generator = generator;
		this.needed = needed;
	}

	public SequenceJSONizer<? super ValueT> getGenerator() {
		return generator;
	}

	public void setGenerator(SequenceJSONizer<? super ValueT> generator) {
		this.generator = generator;
	}

	public IOObjectP<? super ValueT> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super ValueT> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(needed != null ? !needed.testObject(value) : value == null)
			sink.foundNull();
		else {
			sink.beginArray();
			if(generator != null)
				generator.jsonize(value, sink, version);
			sink.endArray();
		}
	}

}
