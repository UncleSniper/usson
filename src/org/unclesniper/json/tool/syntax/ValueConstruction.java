package org.unclesniper.json.tool.syntax;

public class ValueConstruction extends Construction {

	private ComplexValue value;

	public ValueConstruction(int offset, ComplexValue value) {
		super(offset < 0 && value != null ? value.getOffset() : offset);
		this.value = value;
	}

	public ComplexValue getValue() {
		return value;
	}

	public void setValue(ComplexValue value) {
		this.value = value;
	}

}
