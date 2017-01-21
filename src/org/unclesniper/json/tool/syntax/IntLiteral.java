package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;

public class IntLiteral extends AtomicValue {

	private long value;

	public IntLiteral(int offset, long value) {
		super(offset);
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public Value eval(TransformationContext context) {
		return new JSONValue(new JSONInteger(value));
	}

}
