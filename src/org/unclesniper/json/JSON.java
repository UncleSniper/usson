package org.unclesniper.json;

import java.io.IOException;

public interface JSON {

	public static final int TYPE_STRING    = 0001;
	public static final int TYPE_INTEGER   = 0002;
	public static final int TYPE_FRACTION  = 0004;
	public static final int TYPE_NUMBER    = 0006;
	public static final int TYPE_BOOLEAN   = 0010;
	public static final int TYPE_NULL      = 0020;
	public static final int TYPE_PRIMITIVE = 0037;
	public static final int TYPE_ARRAY     = 0040;
	public static final int TYPE_OBJECT    = 0100;
	public static final int TYPE_COMPOUND  = 0140;

	boolean takesPairs();

	void takeElement(JSON value);

	void takePair(String key, JSON value);

	void sinkJSON(JSONSink sink) throws IOException;

	int getJSONType();

}
