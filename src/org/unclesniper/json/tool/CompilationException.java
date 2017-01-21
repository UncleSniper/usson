package org.unclesniper.json.tool;

public abstract class CompilationException extends Exception {

	private final int offset;

	public CompilationException(String message, int offset) {
		super(message);
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

}
