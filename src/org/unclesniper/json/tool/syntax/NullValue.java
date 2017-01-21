package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONNull;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;

public class NullValue extends AtomicValue {

	public NullValue(int offset) {
		super(offset);
	}

	public Value eval(TransformationContext context) {
		return new JSONValue(JSONNull.instance);
	}

}
