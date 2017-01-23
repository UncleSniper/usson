package org.unclesniper.json.tool.values;

import java.util.List;
import java.util.LinkedList;

public class PropertyComponentSet extends Value {

	private final List<PropertyComponent> components = new LinkedList<PropertyComponent>();

	public PropertyComponentSet() {}

	public Iterable<PropertyComponent> getComponents() {
		return components;
	}

	public int getComponentCount() {
		return components.size();
	}

	public PropertyComponent getComponent(int index) {
		return components.get(index);
	}

	public void addComponent(PropertyComponent component) {
		components.add(component);
	}

	public Type getType() {
		return Type.PROPERTY_COMPONENT_SET;
	}

	public boolean isTrue() {
		return true;
	}

}
