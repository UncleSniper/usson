package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.ByteP;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ByteGetter;

public class BytePropertyJSONizer<BaseT> implements JSONizer<BaseT> {

	private ByteGetter<? super BaseT> getter;

	private ByteP needed;

	public BytePropertyJSONizer(ByteGetter<? super BaseT> getter, ByteP needed) {
		this.getter = getter;
		this.needed = needed;
	}

	public ByteGetter<? super BaseT> getGetter() {
		return getter;
	}

	public void setGetter(ByteGetter<? super BaseT> getter) {
		this.getter = getter;
	}

	public ByteP getNeeded() {
		return needed;
	}

	public void setNeeded(ByteP needed) {
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
