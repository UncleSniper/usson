package org.unclesniper.json.tool.values;

import org.unclesniper.json.JSON;
import org.unclesniper.json.JSONObject;

public class PropertyComponent extends Component {

	private final String key;

	private final JSONObject object;

	public PropertyComponent(String key, JSON value, JSONObject object) {
		super(value);
		this.key = key;
		this.object = object;
	}

	public String getKey() {
		return key;
	}

	public JSONObject getObject() {
		return object;
	}

}
