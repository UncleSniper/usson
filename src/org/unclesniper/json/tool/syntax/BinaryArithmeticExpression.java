package org.unclesniper.json.tool.syntax;

public class BinaryArithmeticExpression extends BinaryOperation {

	public enum Operator {
		ADD,
		SUBTRACT,
		MULTIPLY,
		DIVIDE,
		MODULO
	}

	private Operator operator;

	public BinaryArithmeticExpression(int offset,
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
