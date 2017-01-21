package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public abstract class Construction extends Transform {

	public Construction(int offset) {
		super(offset);
	}

	protected Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException {
		return construct(context);
	}

	public abstract Value construct(TransformationContext context) throws TransformationException;

}
