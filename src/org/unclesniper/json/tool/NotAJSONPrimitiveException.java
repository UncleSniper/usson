package org.unclesniper.json.tool;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;

public class NotAJSONPrimitiveException extends TransformationException {

	private final Value value;

	public NotAJSONPrimitiveException(int offset, Value value, String action) {
		super("Cannot " + action + " at offset " + offset + ": Specified " + value.getType().getHumanReadable()
				+ " is not a JSON primitive", offset);
		this.value = value;
	}

	public NotAJSONPrimitiveException(int offset, JSON tree, String action) {
		this(offset, new JSONValue(tree), action);
	}

}
