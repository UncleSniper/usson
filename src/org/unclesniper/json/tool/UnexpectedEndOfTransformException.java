package org.unclesniper.json.tool;

public class UnexpectedEndOfTransformException extends SyntaxException {

	private final String expected;

	public UnexpectedEndOfTransformException(String expected, int offset) {
		super("Unexpected end of transform at offset " + offset + ", expected " + expected, offset);
		this.expected = expected;
	}

	public String getExpected() {
		return expected;
	}

}
