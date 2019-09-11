package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class LongJSONizer implements JSONizer<Long> {

	public static final JSONizer<Long> instance = new LongJSONizer();

	private IOObjectP<? super Long> needed;

	public LongJSONizer() {}

	public LongJSONizer(IOObjectP<? super Long> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super Long> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super Long> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Long value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundInteger(value);
	}

}
