package org.unclesniper.json.tool.syntax;

public class RangeIndirectKey extends IndirectKey {

	private SimpleValue lowerBound;

	private SimpleValue upperBound;

	public RangeIndirectKey(int offset, SimpleValue lowerBound, SimpleValue upperBound) {
		super(offset >= 0 ? offset : (lowerBound == null ? -1 : lowerBound.getOffset()));
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public SimpleValue getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(SimpleValue lowerBound) {
		this.lowerBound = lowerBound;
		if(lowerBound != null && getOffset() < 0)
			setOffset(lowerBound.getOffset());
	}

	public SimpleValue getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(SimpleValue upperBound) {
		this.upperBound = upperBound;
	}

}
