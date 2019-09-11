package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class FloatJSONizer implements JSONizer<Float> {

	public static final JSONizer<Float> instance = new FloatJSONizer();

	private IOObjectP<? super Float> needed;

	public FloatJSONizer() {}

	public FloatJSONizer(IOObjectP<? super Float> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super Float> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super Float> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Float value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundFraction(value.doubleValue());
	}

}
