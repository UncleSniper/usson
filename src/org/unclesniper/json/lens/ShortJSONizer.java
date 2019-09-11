package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class ShortJSONizer implements JSONizer<Short> {

	public static final JSONizer<Short> instance = new ShortJSONizer();

	private IOObjectP<? super Short> needed;

	public ShortJSONizer() {}

	public ShortJSONizer(IOObjectP<? super Short> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super Short> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super Short> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Short value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundInteger(value.longValue());
	}

}
