package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONArray;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.NotAJSONValueException;
import org.unclesniper.json.tool.TransformationException;

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

	public Value construct(TransformationContext context) throws TransformationException {
		JSONArray array = new JSONArray();
		for(ComplexValue element : elements) {
			Value value = element.eval(context);
			JSON baked = Transform.bake(value);
			if(baked == null)
				throw new NotAJSONValueException(getOffset(), value, "add element to constructed array");
			array.add(baked);
		}
		return new JSONValue(array);
	}

}
