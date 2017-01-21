package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

public class ArrayConstruction extends Construction {

	private final List<ComplexValue> elements = new LinkedList<ComplexValue>();

	public ArrayConstruction() {
		this(-1);
	}

	public ArrayConstruction(int offset) {
		super(offset);
	}

	public Iterable<ComplexValue> getElements() {
		return elements;
	}

	public void addElement(ComplexValue element) {
		if(elements.isEmpty() && getOffset() < 0)
			setOffset(element.getOffset());
		elements.add(element);
	}

}
