package org.unclesniper.json;

import java.util.List;
import java.io.IOException;
import java.util.LinkedList;

public class MultiJSONSink implements JSONSink {

	private final List<JSONSink> sinks = new LinkedList<JSONSink>();

	public MultiJSONSink(JSONSink... sinks) {
		for(JSONSink sink : sinks)
			if(sink != null)
				this.sinks.add(sink);
	}

	public Iterable<JSONSink> getSinks() {
		return sinks;
	}

	public int getSinkCount() {
		return sinks.size();
	}

	public JSONSink getSink(int index) {
		return sinks.get(index);
	}

	public void addSink(JSONSink sink) {
		if(sink != null)
			sinks.add(sink);
	}

	public void clearSinks() {
		sinks.clear();
	}

	public void foundBoolean(boolean value) throws IOException {
		for(JSONSink sink : sinks)
			sink.foundBoolean(value);
	}

	public void foundNull() throws IOException {
		for(JSONSink sink : sinks)
			sink.foundNull();
	}

	public void foundString(String value) throws IOException {
		for(JSONSink sink : sinks)
			sink.foundString(value);
	}

	public void foundInteger(long value) throws IOException {
		for(JSONSink sink : sinks)
			sink.foundInteger(value);
	}

	public void foundFraction(double value) throws IOException {
		for(JSONSink sink : sinks)
			sink.foundFraction(value);
	}

	public void beginObject() throws IOException {
		for(JSONSink sink : sinks)
			sink.beginObject();
	}

	public void endObject() throws IOException {
		for(JSONSink sink : sinks)
			sink.endObject();
	}

	public void beginArray() throws IOException {
		for(JSONSink sink : sinks)
			sink.beginArray();
	}

	public void endArray() throws IOException {
		for(JSONSink sink : sinks)
			sink.endArray();
	}

}
