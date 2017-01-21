package org.unclesniper.json.tool.syntax;

import java.util.List;
import java.util.LinkedList;

public class DirectSubselector extends Subselector {

	private final List<DirectKey> keys = new LinkedList<DirectKey>();

	public DirectSubselector(int offset) {
		super(offset);
	}

	public Iterable<DirectKey> getKeys() {
		return keys;
	}

	public void addKey(DirectKey key) {
		if(keys.isEmpty() && getOffset() < 0)
			setOffset(key.getOffset());
		keys.add(key);
	}

}
