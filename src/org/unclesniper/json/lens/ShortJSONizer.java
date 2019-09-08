package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class ShortJSONizer implements JSONizer<Short> {

	public static final JSONizer<Short> instance = new ShortJSONizer();

	private ObjectP<? super Short> needed;

	public ShortJSONizer() {}

	public ShortJSONizer(ObjectP<? super Short> needed) {
		this.needed = needed;
	}

	public ObjectP<? super Short> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Short> needed) {
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
