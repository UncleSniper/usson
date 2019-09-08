package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class StringJSONizer implements JSONizer<String> {

	private ObjectP<? super String> needed;

	public StringJSONizer() {}

	public StringJSONizer(ObjectP<? super String> needed) {
		this.needed = needed;
	}

	public ObjectP<? super String> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super String> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(String value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundString(value);
	}

}
