package org.unclesniper.json.tool.syntax;

public class PropertyDirectKey extends DirectKey {

	private String property;

	public PropertyDirectKey(int offset, String property) {
		super(offset);
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
