package org.unclesniper.json.tool.syntax;

public abstract class Syntax {

	private int offset;

	public Syntax(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
