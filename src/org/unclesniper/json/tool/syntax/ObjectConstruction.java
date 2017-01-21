package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;
import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONObject;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.NotAJSONValueException;
import org.unclesniper.json.tool.TransformationException;
import org.unclesniper.json.tool.NotAJSONPrimitiveException;

public class ObjectConstruction extends Construction {

	public static class Binding extends Syntax {

		private SimpleValue key;

		private ComplexValue value;

		private boolean direct;

		public Binding(int offset, SimpleValue key, ComplexValue value, boolean direct) {
			super(offset < 0 ? key.getOffset() : offset);
			this.key = key;
			this.value = value;
			this.direct = direct;
		}

		public SimpleValue getKey() {
			return key;
		}

		public void setKey(SimpleValue key) {
			this.key = key;
		}

		public ComplexValue getValue() {
			return value;
		}

		public void setValue(ComplexValue value) {
			this.value = value;
		}

		public boolean isDirect() {
			return direct;
		}

		public void setDirect(boolean direct) {
			this.direct = direct;
		}

	}

	private final List<Binding> bindings = new LinkedList<Binding>();

	public ObjectConstruction() {
		this(-1);
	}

	public ObjectConstruction(int offset) {
		super(offset);
	}

	public Iterable<Binding> getBindings() {
		return bindings;
	}

	public void addBinding(Binding binding) {
		if(bindings.isEmpty() && getOffset() < 0)
			setOffset(binding.getOffset());
		bindings.add(binding);
	}

	public Value construct(TransformationContext context) throws TransformationException {
		JSONObject object = new JSONObject();
		for(Binding binding : bindings) {
			Value k = binding.getKey().eval(context), v = binding.getValue().eval(context);
			String property = Transform.stringOf(k);
			if(property == null)
				throw new NotAJSONPrimitiveException(getOffset(), k, "use property name in constructed object");
			JSON baked = Transform.bake(v);
			if(baked == null)
				throw new NotAJSONValueException(getOffset(), v, "use property value in constructed object");
			object.put(property, baked);
		}
		return new JSONValue(object);
	}

}
