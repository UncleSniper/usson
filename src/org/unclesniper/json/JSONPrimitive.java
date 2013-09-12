package org.unclesniper.json;

public abstract class JSONPrimitive implements JSON {

	public boolean takesPairs() {
		return false;
	}

	public void takeElement(JSON value) {
		throw new UnsupportedOperationException("JSON primitive does not take children");
	}

	public void takePair(String key, JSON value) {
		throw new UnsupportedOperationException("JSON primitive does not take children");
	}

}
