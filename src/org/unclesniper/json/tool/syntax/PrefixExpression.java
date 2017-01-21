package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class PrefixExpression extends PIExpression {

	public enum Operator {
		POSITIVE,
		NEGATIVE,
		NOT,
		COMPLEMENT
	}

	private Operator operator;

	private ComplexValue operand;

	public PrefixExpression(int offset, Operator operator, ComplexValue operand) {
		super(offset);
		this.operator = operator;
		this.operand = operand;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public ComplexValue getOperand() {
		return operand;
	}

	public void setOperand(ComplexValue operand) {
		this.operand = operand;
	}

	public Value eval(TransformationContext context) throws TransformationException {
		//TODO
		return null;
	}

}
