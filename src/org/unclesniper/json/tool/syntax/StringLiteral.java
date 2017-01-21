package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONString;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;

public class StringLiteral extends AtomicValue {

	private String value;

	public StringLiteral(int offset, String value) {
		super(offset);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Value eval(TransformationContext context) {
		return new JSONValue(new JSONString(value));
	}

}
