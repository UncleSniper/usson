package org.unclesniper.json;

public class MalformedJSONException extends Exception {

	private int line;

	public MalformedJSONException(String message, int line) {
		super(message);
		this.line = line;
	}

	public MalformedJSONException(String message, int line, Throwable cause) {
		super(message, cause);
		this.line = line;
	}

	public int getLineNumber() {
		return line;
	}

}
