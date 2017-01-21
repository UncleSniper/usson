package org.unclesniper.json.tool.values;

import org.unclesniper.json.JSON;

public abstract class Component {

	private final JSON value;

	public Component(JSON value) {
		this.value = value;
	}

	public JSON getValue() {
		return value;
	}

}
