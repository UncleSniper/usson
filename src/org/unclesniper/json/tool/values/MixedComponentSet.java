package org.unclesniper.json.tool.values;

import java.util.List;
import java.util.LinkedList;

public class MixedComponentSet extends Value {

	private final List<Component> components = new LinkedList<Component>();

	public MixedComponentSet() {}

	public Iterable<Component> getComponents() {
		return components;
	}

	public int getComponentCount() {
		return components.size();
	}

	public Component getComponent(int index) {
		return components.get(index);
	}

	public void addComponent(Component component) {
		components.add(component);
	}

	public Type getType() {
		return Type.MIXED_COMPONENT_SET;
	}

	public boolean isTrue() {
		return true;
	}

}
