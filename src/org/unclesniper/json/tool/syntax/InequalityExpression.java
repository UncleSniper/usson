package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class InequalityExpression extends BinaryOperation {

	public enum Operator {
		LESS_THAN,
		LESS_EQUAL,
		GREATER_THAN,
		GREATER_EQUAL
	}

	private Operator operator;

	public InequalityExpression(int offset, ComplexValue leftOperand, Operator operator, ComplexValue rightOperand) {
		super(offset, leftOperand, rightOperand);
		this.operator = operator;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Value eval(TransformationContext context) throws TransformationException {
		//TODO
		return null;
	}

}
