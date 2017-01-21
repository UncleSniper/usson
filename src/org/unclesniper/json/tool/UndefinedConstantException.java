package org.unclesniper.json.tool;

public class UndefinedConstantException extends TransformationException {

	private final String constant;

	public UndefinedConstantException(int offset, String constant) {
		super("Undefined constant '" + constant + "' at offset " + offset, offset);
		this.constant = constant;
	}

	public String getConstant() {
		return constant;
	}

}
