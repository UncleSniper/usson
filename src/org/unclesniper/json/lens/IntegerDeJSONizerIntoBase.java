package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.j8.IOIntSetter;

public abstract class IntegerDeJSONizerIntoBase<BaseT> implements DeJSONizerIntoBase<BaseT> {

	private class IntegerJSONStateInto extends AbstractJSONState {

		private BaseT base;

		public IntegerJSONStateInto(BaseT base, JSONState parent) {
			super(parent);
			this.base = base;
		}

		@Override
		public JSONState foundInteger(long value) throws IOException, UnexpectedJSONException {
			int intValue = (int)value;
			if((long)intValue != value)
				throw new IntegerOutOfRangeException(value, IntegerOutOfRangeException.PrimitiveType.INT);
			setter.setInt(base, intValue);
			return parent;
		}

	}

	private IOIntSetter<? super BaseT> setter;

	public IntegerDeJSONizerIntoBase(IOIntSetter<? super BaseT> setter) {
		this.setter = setter;
	}

	public IOIntSetter<? super BaseT> getSetter() {
		return setter;
	}

	public void setSetter(IOIntSetter<? super BaseT> setter) {
		this.setter = setter;
	}

	@Override
	public JSONState dejsonize(BaseT base, int version, JSONState parent) {
		return new IntegerJSONStateInto(base, parent);
	}

}
