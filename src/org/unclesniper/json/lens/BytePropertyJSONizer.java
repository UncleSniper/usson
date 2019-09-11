package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOByteP;
import org.unclesniper.json.j8.IOByteGetter;

public class BytePropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private IOByteGetter<? super BaseT> getter;

	private IOByteP needed;

	public BytePropertyJSONizer(IOByteGetter<? super BaseT> getter, IOByteP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public IOByteGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(IOByteGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public IOByteP getNeeded() {
		return needed;
	}

	public void setNeeded(IOByteP needed) {
		this.needed = needed;
	}

	@Override
	public void jsonize(BaseT base, JSONSink sink, int version) throws IOException {
		byte value = getter.getByte(base);
		if(needed == null || needed.testByte(value))
			sink.foundInteger(value);
		else
			sink.foundNull();
	}

}
