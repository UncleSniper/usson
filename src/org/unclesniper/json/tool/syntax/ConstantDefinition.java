package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

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

	protected boolean requiresTuT() {
		return false;
	}

	protected int getDesiredTuTType() {
		return JSON.TYPE_ANY;
	}

	protected Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException {
		context.setConstant(constant, value.eval(context));
		return input;
	}

}
