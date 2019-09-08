package org.unclesniper.json.j8;

public class DoubleArrayIterable implements DoubleIterable {

	private double[] array;

	public DoubleArrayIterable(double[] array) {
		this.array = array;
	}

	public double[] getArray() {
		return array;
	}

	public void setArray(double[] array) {
		this.array = array;
	}

	@Override
	public DoubleIterator doubleIterator() {
		return new DoubleArrayIterator(array);
	}

}
