package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectP;

public class DoubleJSONizer implements JSONizer<Double> {

	public static final JSONizer<Double> instance = new DoubleJSONizer();

	private IOObjectP<? super Double> needed;

	public DoubleJSONizer() {}

	public DoubleJSONizer(IOObjectP<? super Double> needed) {
		this.needed = needed;
	}

	public IOObjectP<? super Double> getNeeded() {
		return needed;
	}

	public void setNeeded(IOObjectP<? super Double> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Double value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundFraction(value.doubleValue());
	}

}
