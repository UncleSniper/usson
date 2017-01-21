package org.unclesniper.json.tool;

public abstract class LexicalException extends CompilationException {

	public LexicalException(String message, int offset) {
		super(message, offset);
	}

}
