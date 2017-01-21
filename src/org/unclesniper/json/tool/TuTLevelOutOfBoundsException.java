package org.unclesniper.json.tool;

public class TuTLevelOutOfBoundsException extends TransformationException {

	private final int level;

	public TuTLevelOutOfBoundsException(int offset, int level) {
		super("Cannot access tree-under-transformation level " + level + " at offset " + offset
				+ ": No such level", offset);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

}
