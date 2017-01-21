package org.unclesniper.json.tool.syntax;

import org.unclesniper.json.JSON;
import org.unclesniper.json.tool.values.Value;
import org.unclesniper.json.tool.values.JSONValue;
import org.unclesniper.json.tool.TransformationContext;
import org.unclesniper.json.tool.TransformationException;
import org.unclesniper.json.tool.TuTLevelOutOfBoundsException;

public class ThisValue extends AtomicValue {

	private int level;

	public ThisValue(int offset, int level) {
		super(offset);
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Value eval(TransformationContext context) throws TransformationException {
		JSON tree = context.getTree(level);
		if(tree == null)
			throw new TuTLevelOutOfBoundsException(getOffset(), level);
		return new JSONValue(tree);
	}

}
