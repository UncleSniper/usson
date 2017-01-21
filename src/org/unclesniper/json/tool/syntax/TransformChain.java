package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class TransformChain extends Transform {

	private final List<Transform> transforms = new LinkedList<Transform>();

	public TransformChain() {
		this(-1);
	}

	public TransformChain(int offset) {
		super(offset);
	}

	public Iterable<Transform> getTransforms() {
		return transforms;
	}

	public void addTransform(Transform transform) {
		if(transforms.isEmpty() && getOffset() < 0)
			setOffset(transform.getOffset());
		transforms.add(transform);
	}

	protected Value doTransform(TransformationContext context, JSON tree, Value input)
			throws TransformationException {
		context.pushScope();
		try {
			for(Transform transform : transforms)
				input = transform.transform(context, input);
			return input;
		}
		finally {
			context.popScope();
		}
	}

}
