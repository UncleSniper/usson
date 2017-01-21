package org.unclesniper.json.tool.values;

public abstract class Value {

	public enum Type {

		JSON("JSON tree"),
		PROPERTY_COMPONENT_SET("component set"),
		INDEX_COMPONENT_SET("component set"),
		MIXED_COMPONENT_SET("component set"),
		FUNCTION("function");

		private final String humanReadable;

		private Type(String humanReadable) {
			this.humanReadable = humanReadable;
		}

		public String getHumanReadable() {
			return humanReadable;
		}

	}

	public Value() {}

	public abstract Type getType();

}
