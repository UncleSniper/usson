package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IOObjectSink;

public class IntegerDeJSONizer implements DeJSONizer<Integer> {

	private class IntegerJSONState extends AbstractJSONState {

		private final IOObjectSink<? super Integer> sink;

		public IntegerJSONState(IOObjectSink<? super Integer> sink, JSONState parent) {
			super(parent);
			this.sink = sink;
		}

		@Override
		public JSONState foundNull() throws IOException, UnexpectedJSONException {
			if(nullAllowed) {
				sink.putObject(null);
				return parent;
			}
			return super.foundNull();
		}

		@Override
		public JSONState foundInteger(long value) throws IOException, UnexpectedJSONException {
			int intValue = (int)value;
			if((long)intValue != value)
				throw new IntegerOutOfRangeException(value, IntegerOutOfRangeException.PrimitiveType.INT);
			sink.putObject(intValue);
			return parent;
		}

	}

	public static final DeJSONizer<Integer> instance = new IntegerDeJSONizer();

	private boolean nullAllowed;

	public IntegerDeJSONizer() {
		this(true);
	}

	public IntegerDeJSONizer(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
	}

	public boolean isNullAllowed() {
		return nullAllowed;
	}

	public void setNullAllowed(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
	}

	@Override
	public JSONState dejsonize(IOObjectSink<? super Integer> sink, int version, JSONState parent) {
		return new IntegerJSONState(sink, parent);
	}

}
