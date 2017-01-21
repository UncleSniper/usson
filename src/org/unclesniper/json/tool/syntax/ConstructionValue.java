package org.unclesniper.json.tool.syntax;

public class ConstructionValue extends NonNameValue {

	private Construction construction;

	public ConstructionValue(Construction construction) {
		super(construction == null ? -1 : construction.getOffset());
		this.construction = construction;
	}

	public Construction getConstruction() {
		return construction;
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
		if(construction != null && getOffset() < 0)
			setOffset(construction.getOffset());
	}

}
