package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class BooleanJSONizer implements JSONizer<Boolean> {

	public static final JSONizer<Boolean> instance = new BooleanJSONizer();

	private ObjectP<? super Boolean> needed;

	public BooleanJSONizer() {}

	public BooleanJSONizer(ObjectP<? super Boolean> needed) {
		this.needed = needed;
	}

	public ObjectP<? super Boolean> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Boolean> needed) {
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
