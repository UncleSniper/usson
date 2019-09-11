package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class BooleanJSONizer implements JSONizer<Boolean> {

	public static final JSONizer<Boolean> instance = new BooleanJSONizer();

	private IOObjectP<? super Boolean> needed;

	public BooleanJSONizer() {}

	public BooleanJSONizer(IOObjectP<? super Boolean> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super Boolean> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super Boolean> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Boolean value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundBoolean(value);
	}

}
