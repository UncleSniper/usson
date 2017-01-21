package org.unclesniper.json.tool.syntax;

public class EqualityExpression extends BinaryOperation {

	public enum Operator {
		EQUAL,
		UNEQUAL
	}

	private Operator operator;

	public EqualityExpression(int offset, ComplexValue leftOperand, Operator operator, ComplexValue rightOperand) {
		super(offset, leftOperand, rightOperand);
		this.operator = operator;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

}
