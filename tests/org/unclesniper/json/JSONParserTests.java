package org.unclesniper.json;

import org.junit.Test;

public class JSONParserTests {

	@Test(expected = NullPointerException.class)
	public void nullSink() {
		new JSONParser(null);
	}

	@Test(expected = MalformedJSONException.class)
	public void emptyDocument() throws MalformedJSONException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial(new char[0], 0, 0);
		parser.endDocument();
	}

}
