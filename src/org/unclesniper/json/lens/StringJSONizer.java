package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class StringJSONizer implements JSONizer<String> {

	public static final JSONizer<String> instance = new StringJSONizer();

	private IOObjectP<? super String> needed;

	public StringJSONizer() {}

	public StringJSONizer(IOObjectP<? super String> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super String> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super String> needed) {
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
