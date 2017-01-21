package org.unclesniper.json.tool;

public class UnexpectedCharacterException extends LexicalException {

	public UnexpectedCharacterException(int offset) {
		super("Unexpected character at offset " + offset, offset);
	}

}
