package org.unclesniper.json.tool.syntax;

public class FloatLiteral extends AtomicValue {

	private double value;

	public FloatLiteral(int offset, double value) {
		super(offset);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
