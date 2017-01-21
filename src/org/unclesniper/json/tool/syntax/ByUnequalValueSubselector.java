package org.unclesniper.json.tool.syntax;

public class ByUnequalValueSubselector extends ByValueSubselector {

	private InequalityExpression.Operator operator;

	private SimpleValue rightOperand;

	public ByUnequalValueSubselector(int offset, InequalityExpression.Operator operator, SimpleValue rightOperand) {
		super(offset);
		this.operator = operator;
		this.rightOperand = rightOperand;
	}

	public InequalityExpression.Operator getOperator() {
		return operator;
	}

	public void setOperator(InequalityExpression.Operator operator) {
		this.operator = operator;
	}

	public SimpleValue getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(SimpleValue rightOperand) {
		this.rightOperand = rightOperand;
	}

}
