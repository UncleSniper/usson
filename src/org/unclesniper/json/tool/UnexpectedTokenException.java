package org.unclesniper.json.tool;

public class UnexpectedTokenException extends SyntaxException {

	private final Token unexpected;

	private final String expected;

	public UnexpectedTokenException(Token unexpected, String expected) {
		super("Unexpected " + unexpected.getType().render() + " token at offset " + unexpected.getOffset()
				+ ", expected " + expected, unexpected.getOffset());
		this.unexpected = unexpected;
		this.expected = expected;
	}

	public Token getUnexpected() {
		return unexpected;
	}

	public String getExpected() {
		return expected;
	}

}
