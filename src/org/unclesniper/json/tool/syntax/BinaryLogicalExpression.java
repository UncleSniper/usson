package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSONBoolean;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

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

	public Value eval(TransformationContext context) throws TransformationException {
		boolean l = getLeftOperand().eval(context).isTrue(), r = getRightOperand().eval(context).isTrue();
		boolean result;
		switch(operator) {
			case CONJUNCTION:
				result = l && r;
				break;
			case DISJUNCTION:
				result = l || r;
				break;
			default:
				throw new Error("Unrecognized Operator: " + operator.name());
		}
		return new JSONValue(new JSONBoolean(result));
	}

}
