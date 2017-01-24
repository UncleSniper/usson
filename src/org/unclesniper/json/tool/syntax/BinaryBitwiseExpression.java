package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONInteger;
import org.unclesniper.json.tool.ECMAUtils;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;
import org.unclesniper.json.tool.WrongOperandTypeException;

public class BinaryBitwiseExpression extends BinaryOperation {

	public enum Operator {

		CONJUNCTION("_ & _"),
		DISJUNCTION("_ | _"),
		XOR("_ ^ _");

		private final String humanReadable;

		private Operator(String humanReadable) {
			this.humanReadable = humanReadable;
		}

		public String getHumanReadable() {
			return humanReadable;
		}

	}

	private Operator operator;

	public BinaryBitwiseExpression(int offset,
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
		Value l = getLeftOperand().eval(context), r = getRightOperand().eval(context);
		if(l.getType() != Value.Type.JSON)
			throw new WrongOperandTypeException(getOffset(), operator.getHumanReadable(), "left",
					"integer", l.getType().getHumanReadable());
		if(r.getType() != Value.Type.JSON)
			throw new WrongOperandTypeException(getOffset(), operator.getHumanReadable(), "right",
					"integer", r.getType().getHumanReadable());
		int li = ECMAUtils.toInt32(((JSONValue)l).getJSON());
		int ri = ECMAUtils.toInt32(((JSONValue)r).getJSON());
		int result;
		switch(operator) {
			case CONJUNCTION:
				result = li & ri;
				break;
			case DISJUNCTION:
				result = li | ri;
				break;
			case XOR:
				result = li ^ ri;
				break;
			default:
				throw new Error("Unrecognized Operator: " + operator.name());
		}
		return new JSONValue(new JSONInteger(result));
	}

}
