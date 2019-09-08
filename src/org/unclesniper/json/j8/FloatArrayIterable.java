package org.unclesniper.json.j8;

public class FloatArrayIterable implements FloatIterable {

	private float[] array;

	public FloatArrayIterable(float[] array) {
		this.array = array;
	}

	public float[] getArray() {
		return array;
	}

	public void setArray(float[] array) {
		this.array = array;
	}

	@Override
	public FloatIterator floatIterator() {
		return new FloatArrayIterator(array);
	}

	@Override
	public DoubleIterator doubleIterator() {
		return new FloatIteratorAdapter(new FloatArrayIterator(array));
	}

}
