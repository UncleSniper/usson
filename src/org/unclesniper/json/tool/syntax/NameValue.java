package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;
import org.unclesniper.json.tool.UndefinedConstantException;

public class NameValue extends UntransformedValue {

	private String name;

	public NameValue(int offset, String name) {
		super(offset);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Value eval(TransformationContext context) throws TransformationException {
		Value value = context.getConstant(name);
		if(value == null)
			throw new UndefinedConstantException(getOffset(), name);
		return value;
	}

}
