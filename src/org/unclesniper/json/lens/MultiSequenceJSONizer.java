package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.ArrayIterable;

public class MultiSequenceJSONizer<ValueT> implements SequenceJSONizer<ValueT> {

	private Iterable<? extends SequenceJSONizer<? super ValueT>> children;

	public MultiSequenceJSONizer(Iterable<? extends SequenceJSONizer<? super ValueT>> children) {
		this.children = children;
	}

	public MultiSequenceJSONizer(SequenceJSONizer<? super ValueT>... children) {
		setChildren(children);
	}

	public Iterable<? extends SequenceJSONizer<? super ValueT>> getChildren() {
		return children;
	}

	public void setChildren(Iterable<? extends SequenceJSONizer<? super ValueT>> children) {
		this.children = children;
	}

	public void setChildren(SequenceJSONizer<? super ValueT>[] children) {
		this.children = children == null || children.length == 0 ? null
				: new ArrayIterable<SequenceJSONizer<? super ValueT>>(children);
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(children == null)
			return;
		for(SequenceJSONizer<? super ValueT> child : children)
			child.jsonize(value, sink, version);
	}

}
