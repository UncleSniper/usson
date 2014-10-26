package org.unclesniper.json;

import java.io.IOException;

public class JSONFraction extends JSONNumber {

	private double value;

	public JSONFraction(double value) {
		this.value = value;
	}

	public double doubleValue() {
		return value;
	}

	public Double numberValue() {
		return value;
	}

	public void sinkJSON(JSONSink sink) throws IOException {
		sink.foundFraction(value);
	}

	public int getJSONType() {
		return JSON.TYPE_FRACTION;
	}

}
