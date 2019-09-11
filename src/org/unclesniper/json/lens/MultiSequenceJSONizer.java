package org.unclesniper.json.lens;

import java.io.IOException;
import org.unclesniper.json.JSONSink;
import org.unclesniper.json.j8.IOObjectIterable;
import org.unclesniper.json.j8.IOObjectIterator;
import org.unclesniper.json.j8.ArrayObjectIterable;

public class MultiSequenceJSONizer<ValueT> implements SequenceJSONizer<ValueT> {

	private IOObjectIterable<? extends SequenceJSONizer<? super ValueT>> children;

	public MultiSequenceJSONizer(IOObjectIterable<? extends SequenceJSONizer<? super ValueT>> children) {
		this.children = children;
	}

	public MultiSequenceJSONizer(SequenceJSONizer<? super ValueT>... children) {
		setChildren(children);
	}

	public IOObjectIterable<? extends SequenceJSONizer<? super ValueT>> getChildren() {
		return children;
	}

	public void setChildren(IOObjectIterable<? extends SequenceJSONizer<? super ValueT>> children) {
		this.children = children;
	}

	public void setChildren(SequenceJSONizer<? super ValueT>[] children) {
		this.children = children == null || children.length == 0 ? null
				: new ArrayObjectIterable<SequenceJSONizer<? super ValueT>>(children);
	}

	@Override
	public void jsonize(ValueT value, JSONSink sink, int version) throws IOException {
		if(children == null)
			return;
		IOObjectIterator<? extends SequenceJSONizer<? super ValueT>> it = children.objectIterator();
		while(it.hasNext())
			it.next().jsonize(value, sink, version);
	}

}
