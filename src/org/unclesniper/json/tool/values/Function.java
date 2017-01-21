package org.unclesniper.json.tool.values;

import org.unclesniper.json.tool.TransformationContext;

public abstract class Function extends Value {

	private final String name;

	public Function(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return Type.FUNCTION;
	}

	public abstract Value call(TransformationContext context, int callOffset, Value[] arguments);

}
