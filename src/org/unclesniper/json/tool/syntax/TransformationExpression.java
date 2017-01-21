package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class TransformationExpression extends SimpleValue {

	private ComplexValue subject;

	private final List<Transform> transforms = new LinkedList<Transform>();

	public TransformationExpression(int offset, ComplexValue subject) {
		super(offset < 0 && subject != null ? subject.getOffset() : offset);
		this.subject = subject;
	}

	public ComplexValue getSubject() {
		return subject;
	}

	public void setSubject(ComplexValue subject) {
		this.subject = subject;
	}

	public Iterable<Transform> getTransforms() {
		return transforms;
	}

	public void addTransform(Transform transform) {
		transforms.add(transform);
	}

	public Value eval(TransformationContext context) throws TransformationException {
		Value result = subject.eval(context);
		for(Transform transform : transforms)
			result = transform.transform(context, result);
		return result;
	}

}
