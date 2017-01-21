package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class ValueConstruction extends Construction {

	private ComplexValue value;

	public ValueConstruction(int offset, ComplexValue value) {
		super(offset < 0 && value != null ? value.getOffset() : offset);
		this.value = value;
	}

	public ComplexValue getValue() {
		return value;
	}

	public void setValue(ComplexValue value) {
		this.value = value;
	}

	public Value construct(TransformationContext context) throws TransformationException {
		return value.eval(context);
	}

}
