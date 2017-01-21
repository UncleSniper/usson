package org.unclesniper.json.tool.syntax;

public class IndexDirectKey extends DirectKey {

	private int index;

	public IndexDirectKey(int offset, int index) {
		super(offset);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
