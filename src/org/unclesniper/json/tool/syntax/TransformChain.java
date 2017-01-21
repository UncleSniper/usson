package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

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

}
