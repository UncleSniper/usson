package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

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

}
