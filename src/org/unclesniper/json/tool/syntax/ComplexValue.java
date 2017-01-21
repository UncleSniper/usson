package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public abstract class ComplexValue extends Syntax {

	public ComplexValue(int offset) {
		super(offset);
	}

	public abstract Value eval(TransformationContext context) throws TransformationException;

}
