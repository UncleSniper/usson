package org.unclesniper.json.tool.syntax;

public abstract class WithComponent extends Transform {

	private Selector selector;

	public WithComponent(Selector selector) {
		super(selector == null ? -1 : selector.getOffset());
		this.selector = selector;
		if(selector != null)
			setOffset(selector.getOffset());
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
		if(selector != null && getOffset() < 0)
			setOffset(selector.getOffset());
	}

}
