package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;

public class SelectedValue extends NonNameValue {

	private ComplexValue value;

	private Selector selector;

	public SelectedValue(int offset, ComplexValue value, Selector selector) {
		super(offset >= 0 ? offset : (value == null ? -1 : value.getOffset()));
		this.value = value;
		this.selector = selector;
	}

	public ComplexValue getValue() {
		return value;
	}

	public void setValue(ComplexValue value) {
		this.value = value;
		if(value != null && getOffset() < 0)
			setOffset(value.getOffset());
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public Value eval(TransformationContext context) throws TransformationException {
		Value result = value.eval(context);
		//TODO: apply selector
		return result;
	}

}
