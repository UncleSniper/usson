package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;

public class BoolLiteral extends AtomicValue {

	private boolean value;

	public BoolLiteral(int offset, boolean value) {
		super(offset);
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public Value eval(TransformationContext context) {
		return new JSONValue(new JSONBoolean(value));
	}

}
