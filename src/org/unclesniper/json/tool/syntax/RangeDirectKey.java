package org.unclesniper.json.tool.syntax;

public class RangeDirectKey extends DirectKey {

	private int lowerBound;

	private int upperBound;

	public RangeDirectKey(int offset, int lowerBound, int upperBound) {
		super(offset);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

}
