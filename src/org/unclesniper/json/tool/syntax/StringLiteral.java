package org.unclesniper.json.tool.syntax;

public class StringLiteral extends AtomicValue {

	private String value;

	public StringLiteral(int offset, String value) {
		super(offset);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
