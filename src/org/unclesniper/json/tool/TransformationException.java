package org.unclesniper.json.tool;

public abstract class TransformationException extends Exception {

	private final int offset;

	public TransformationException(String message, int offset) {
		super(message);
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

}
