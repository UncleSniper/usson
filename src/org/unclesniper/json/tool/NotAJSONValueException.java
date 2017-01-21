package org.unclesniper.json.tool;

import org.unclesniper.json.tool.values.Value;

public class NotAJSONValueException extends TransformationException {

	private final Value value;

	public NotAJSONValueException(int offset, Value value, String action) {
		super("Cannot " + action + " at offset " + offset + ": A " + value.getType().getHumanReadable()
				+ " cannot be baked into a JSON tree", offset);
		this.value = value;
	}

	public Value getValue() {
		return value;
	}

}
