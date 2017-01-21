package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class RetainComponents extends WithComponent {

	public RetainComponents(int offset, Selector selector) {
		super(selector);
		if(offset >= 0)
			setOffset(offset);
	}

	protected Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException {
		//TODO
		return null;
	}

}
