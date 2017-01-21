package org.unclesniper.json.tool.syntax;

public abstract class BinaryOperation extends PIExpression {

	private ComplexValue leftOperand;

	private ComplexValue rightOperand;

	public BinaryOperation(int offset, ComplexValue leftOperand, ComplexValue rightOperand) {
		super(offset >= 0 ? offset : (leftOperand == null ? -1 : leftOperand.getOffset()));
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public ComplexValue getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(ComplexValue leftOperand) {
		this.leftOperand = leftOperand;
		if(leftOperand != null && getOffset() < 0)
			setOffset(leftOperand.getOffset());
	}

	public ComplexValue getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(ComplexValue rightOperand) {
		this.rightOperand = rightOperand;
	}

}
