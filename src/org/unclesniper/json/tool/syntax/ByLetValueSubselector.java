package org.unclesniper.json.tool.syntax;

public class ByLetValueSubselector extends ByValueSubselector {

	private String name;

	private SelectedValue condition;

	public ByLetValueSubselector(int offset, String name, SelectedValue condition) {
		super(offset);
		this.name = name;
		this.condition = condition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SelectedValue getCondition() {
		return condition;
	}

	public void setCondition(SelectedValue condition) {
		this.condition = condition;
	}

}
