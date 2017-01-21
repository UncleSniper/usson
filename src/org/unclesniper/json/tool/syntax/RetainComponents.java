package org.unclesniper.json.tool.syntax;

public class RetainComponents extends WithComponent {

	public RetainComponents(int offset, Selector selector) {
		super(selector);
		if(offset >= 0)
			setOffset(offset);
	}

}
