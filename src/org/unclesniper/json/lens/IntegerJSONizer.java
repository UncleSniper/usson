package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class IntegerJSONizer implements JSONizer<Integer> {

	public static final JSONizer<Integer> instance = new IntegerJSONizer();

	private ObjectP<? super Integer> needed;

	public IntegerJSONizer() {}

	public IntegerJSONizer(ObjectP<? super Integer> needed) {
		this.needed = needed;
	}

	public ObjectP<? super Integer> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Integer> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Integer value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundInteger(value.longValue());
	}

}
