package org.unclesniper.json.tool;

public abstract class SyntaxException extends CompilationException {

	public SyntaxException(String message, int offset) {
		super(message, offset);
	}

}
