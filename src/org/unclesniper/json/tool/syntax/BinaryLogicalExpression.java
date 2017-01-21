package org.unclesniper.json.tool.syntax;

public class BinaryLogicalExpression extends BinaryOperation {

	public enum Operator {
		CONJUNCTION,
		DISJUNCTION
	}

	private Operator operator;

	public BinaryLogicalExpression(int offset,
			ComplexValue leftOperand, Operator operator, ComplexValue rightOperand) {
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
