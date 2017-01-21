package org.unclesniper.json.tool.syntax;

public class NameValue extends UntransformedValue {

	private String name;

	public NameValue(int offset, String name) {
		super(offset);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
