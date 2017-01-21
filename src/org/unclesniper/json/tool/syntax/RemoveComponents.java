package org.unclesniper.json.tool.syntax;

public class RemoveComponents extends WithComponent {

	public RemoveComponents(int offset, Selector selector) {
		super(selector);
		if(offset >= 0)
			setOffset(offset);
	}

}
