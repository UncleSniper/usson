package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

public class CallExpression extends PIExpression {

	private ComplexValue subject;

	private final List<ComplexValue> arguments = new LinkedList<ComplexValue>();

	public CallExpression(int offset, ComplexValue subject) {
		super(offset < 0 && subject != null ? subject.getOffset() : offset);
		this.subject = subject;
	}

	public ComplexValue getSubject() {
		return subject;
	}

	public void setSubject(ComplexValue subject) {
		this.subject = subject;
	}

	public Iterable<ComplexValue> getArguments() {
		return arguments;
	}

	public void addArgument(ComplexValue argument) {
		arguments.add(argument);
	}

}
