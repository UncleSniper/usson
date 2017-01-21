package org.unclesniper.json.tool.syntax;

public class ByEqualValueSubselector extends ByValueSubselector {

	private EqualityExpression.Operator operator;

	private SimpleValue rightOperand;

	public ByEqualValueSubselector(int offset, EqualityExpression.Operator operator, SimpleValue rightOperand) {
		super(offset);
		this.operator = operator;
		this.rightOperand = rightOperand;
	}

	public EqualityExpression.Operator getOperator() {
		return operator;
	}

	public void setOperator(EqualityExpression.Operator operator) {
		this.operator = operator;
	}

	public SimpleValue getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(SimpleValue rightOperand) {
		this.rightOperand = rightOperand;
	}

}
