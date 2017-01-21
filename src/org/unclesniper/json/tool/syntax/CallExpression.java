package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.Function;
import org.unclesniper.json.tool.NotAFunctionException;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

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

	public Value eval(TransformationContext context) throws TransformationException {
		Value function = subject.eval(context);
		if(function.getType() != Value.Type.FUNCTION)
			throw new NotAFunctionException(getOffset(), function);
		Function f = (Function)function;
		Value[] args = new Value[arguments.size()];
		int index = 0;
		for(ComplexValue argument : arguments)
			args[index++] = argument.eval(context);
		return f.call(context, getOffset(), args);
	}

}
