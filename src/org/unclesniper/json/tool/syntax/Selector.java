package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

public class Selector extends Syntax {

	private final List<Subselector> subselectors = new LinkedList<Subselector>();

	public Selector() {
		this(-1);
	}

	public Selector(int offset) {
		super(offset);
	}

	public Iterable<Subselector> getSubselectors() {
		return subselectors;
	}

	public void addSubselector(Subselector subselector) {
		if(subselectors.isEmpty() && getOffset() < 0)
			setOffset(subselector.getOffset());
		subselectors.add(subselector);
	}

}
