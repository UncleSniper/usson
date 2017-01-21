package org.unclesniper.json.tool.syntax;

public class ConstantDefinition extends Transform {

	private String constant;

	private ComplexValue value;

	public ConstantDefinition(int offset, String constant, ComplexValue value) {
		super(offset);
		this.constant = constant;
		this.value = value;
	}

	public String getConstant() {
		return constant;
	}

	public void setConstant(String constant) {
		this.constant = constant;
	}

	public ComplexValue getValue() {
		return value;
	}

	public void setValue(ComplexValue value) {
		this.value = value;
	}

}
