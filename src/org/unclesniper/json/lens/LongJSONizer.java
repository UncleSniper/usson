package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class LongJSONizer implements JSONizer<Long> {

	public static final JSONizer<Long> instance = new LongJSONizer();

	private ObjectP<? super Long> needed;

	public LongJSONizer() {}

	public LongJSONizer(ObjectP<? super Long> needed) {
		this.needed = needed;
	}

	public ObjectP<? super Long> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Long> needed) {
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
