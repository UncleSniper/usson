package org.unclesniper.json.j8;

public class BooleanArrayIterable implements BooleanIterable {

	private boolean[] array;

	public BooleanArrayIterable(boolean[] array) {
		this.array = array;
	}

	public boolean[] getArray() {
		return array;
	}

	public void setArray(boolean[] array) {
		this.array = array;
	}

	@Override
	public BooleanIterator booleanIterator() {
		return new BooleanArrayIterator(array);
	}

}
