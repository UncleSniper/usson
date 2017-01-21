package org.unclesniper.json.tool.syntax;

public class IntLiteral extends AtomicValue {

	private long value;

	public IntLiteral(int offset, long value) {
		super(offset);
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
