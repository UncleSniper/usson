package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONFraction;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;

public class FloatLiteral extends AtomicValue {

	private double value;

	public FloatLiteral(int offset, double value) {
		super(offset);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Value eval(TransformationContext context) {
		return new JSONValue(new JSONFraction(value));
	}

}
