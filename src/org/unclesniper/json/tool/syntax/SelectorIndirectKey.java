package org.unclesniper.json.tool.syntax;

public class SelectorIndirectKey extends IndirectKey {

	private SimpleValue value;

	private Selector selector;

	public SelectorIndirectKey(SimpleValue value, Selector selector) {
		super(value == null ? -1 : value.getOffset());
		this.value = value;
		this.selector = selector;
	}

	public SimpleValue getValue() {
		return value;
	}

	public void setValue(SimpleValue value) {
		this.value = value;
		if(value != null && getOffset() < 0)
			setOffset(value.getOffset());
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

}
