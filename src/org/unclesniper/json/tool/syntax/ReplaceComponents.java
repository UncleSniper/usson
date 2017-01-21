package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

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

	protected Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException {
		//TODO
		return null;
	}

}
