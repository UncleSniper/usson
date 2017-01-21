package org.unclesniper.json.tool.values;

import java.util.List;
import java.util.LinkedList;

public class IndexComponentSet extends Value {

	private final List<IndexComponent> components = new LinkedList<IndexComponent>();

	public IndexComponentSet() {}

	public Iterable<IndexComponent> getComponents() {
		return components;
	}

	public int getComponentCount() {
		return components.size();
	}

	public IndexComponent getComponent(int index) {
		return components.get(index);
	}

	public void addComponent(IndexComponent component) {
		components.add(component);
	}

	public Type getType() {
		return Type.INDEX_COMPONENT_SET;
	}

}
