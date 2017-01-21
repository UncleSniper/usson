package org.unclesniper.json.tool.syntax;

public class ThisValue extends AtomicValue {

	private int level;

	public ThisValue(int offset, int level) {
		super(offset);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
