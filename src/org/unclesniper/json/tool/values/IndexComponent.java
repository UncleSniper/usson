package org.unclesniper.json.tool.values;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONArray;

public class IndexComponent extends Component {

	private final int key;

	private final JSONArray array;

	public IndexComponent(int key, JSON value, JSONArray array) {
		super(value);
		this.key = key;
		this.array = array;
	}

	public int getKey() {
		return key;
	}

	public JSONArray getArray() {
		return array;
	}

}
