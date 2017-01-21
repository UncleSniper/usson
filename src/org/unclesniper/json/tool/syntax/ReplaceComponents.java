package org.unclesniper.json.tool.syntax;

public class ReplaceComponents extends WithComponent {

	private ComplexValue replacement;

	public ReplaceComponents(int offset, Selector selector, ComplexValue replacement) {
		super(selector);
		if(offset >= 0)
			setOffset(offset);
		this.replacement = replacement;
	}

	public ComplexValue getReplacement() {
		return replacement;
	}

}
