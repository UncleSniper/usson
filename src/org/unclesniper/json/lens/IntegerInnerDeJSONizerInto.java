package org.unclesniper.json.lens;

import org.unclesniper.json.j8.IOIntSetter;

public class IntegerInnerDeJSONizerInto<BaseT>
		extends IntegerDeJSONizerIntoBase<BaseT> implements InnerDeJSONizerInto<BaseT> {

	private String propertyName;

	public IntegerInnerDeJSONizerInto(String propertyName, IOIntSetter<? super BaseT> setter) {
		super(setter);
		this.propertyName = propertyName;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
