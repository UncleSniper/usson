package org.unclesniper.json;

import java.io.IOException;

public class JSONFraction extends JSONNumber {

	public static final JSONFraction NaN = new JSONFraction(Double.NaN);

	public static final JSONFraction POSITIVE_INFINITY = new JSONFraction(Double.POSITIVE_INFINITY);

	public static final JSONFraction NEGATIVE_INFINITY = new JSONFraction(Double.NEGATIVE_INFINITY);

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
