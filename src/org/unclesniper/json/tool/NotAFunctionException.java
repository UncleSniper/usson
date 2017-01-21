package org.unclesniper.json.tool;

import org.unclesniper.json.tool.values.Value;

public class NotAFunctionException extends TransformationException {

	private final Value value;

	public NotAFunctionException(int offset, Value value) {
		super("Cannot call value at offset " + offset + ": A " + value.getType().getHumanReadable()
				+ " is not a function", offset);
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

}
