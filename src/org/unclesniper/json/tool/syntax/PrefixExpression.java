package org.unclesniper.json.tool.syntax;

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

}
