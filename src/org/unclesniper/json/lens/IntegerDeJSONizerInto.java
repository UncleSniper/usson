package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IOIntSetter;

public class IntegerDeJSONizerInto<BaseT>
		extends IntegerDeJSONizerIntoBase<BaseT> implements DeJSONizerInto<BaseT> {

	public IntegerDeJSONizerInto(IOIntSetter<? super BaseT> setter) {
		super(setter);
	}

}
