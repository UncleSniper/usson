package org.unclesniper.json.tool;

public class UnexpectedEndOfInputException extends LexicalException {

	private final int startOffset;

	public UnexpectedEndOfInputException(int offset, int startOffset) {
		super("Unexpected end of transform input at offset " + offset
				+ " (token starts at offset " + startOffset + ")", offset);
		this.startOffset = startOffset;
	}

	public int getStartOffset() {
		return startOffset;
	}

}
