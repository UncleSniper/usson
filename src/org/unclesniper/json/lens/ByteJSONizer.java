package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ObjectP;

public class ByteJSONizer implements JSONizer<Byte> {

	public static final JSONizer<Byte> instance = new ByteJSONizer();

	private ObjectP<? super Byte> needed;

	public ByteJSONizer() {}

	public ByteJSONizer(ObjectP<? super Byte> needed) {
		this.needed = needed;
	}

	public ObjectP<? super Byte> getNeeded() {
		return needed;
	}

	public void setNeeded(ObjectP<? super Byte> needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(Byte value, JSONSink sink, int version) throws IOException {
		if(value == null || (needed != null && !needed.testObject(value)))
			sink.foundNull();
		else
			sink.foundInteger(value.longValue());
	}

}
