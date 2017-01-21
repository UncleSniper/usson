package org.unclesniper.json.tool.syntax;

public class ValueIndirectKey extends IndirectKey {

	private SimpleValue value;

	public ValueIndirectKey(SimpleValue value) {
		super(value == null ? -1 : value.getOffset());
		this.value = value;
	}

	public SimpleValue getValue() {
		return value;
	}

	public void setValue(SimpleValue value) {
		this.value = value;
		if(value != null && getOffset() < 0)
			setOffset(value.getOffset());
	}

}
