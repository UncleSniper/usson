package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

public class IndirectSubselector extends Subselector {

	private final List<IndirectKey> keys = new LinkedList<IndirectKey>();

	public IndirectSubselector(int offset) {
		super(offset);
	}

	public Iterable<IndirectKey> getKeys() {
		return keys;
	}

	public void addKey(IndirectKey key) {
		if(keys.isEmpty() && getOffset() < 0)
			setOffset(key.getOffset());
		keys.add(key);
	}

}
