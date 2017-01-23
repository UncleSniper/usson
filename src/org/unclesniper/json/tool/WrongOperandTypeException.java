package org.unclesniper.json.tool;

public class WrongOperandTypeException extends TransformationException {

	private final String operator;

	private final String operand;

	private final String expectedType;

	private final String foundType;

	public WrongOperandTypeException(int offset,
			String operator, String operand, String expectedType, String foundType) {
		super("Wrong operand type at offset " + offset + ": The " + (operand == null || operand.length() == 0
				? "operand" : operand + " operand") + " to the '" + operator + "' operator must be a "
				+ expectedType + ", not a " + foundType, offset);
		this.operator = operator;
		this.operand = operand;
		this.expectedType = expectedType;
		this.foundType = foundType;
	}

	public String getOperator() {
		return operator;
	}

	public String getOperand() {
		return operand;
	}

	public String getExpectedType() {
		return expectedType;
	}

	public String getFoundType() {
		return foundType;
	}

}
