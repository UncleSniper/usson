package org.unclesniper.json;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JSONParserTests {

	static class StoringParser extends JSONParser {

		char[] data;

		Integer offset;

		Integer count;

		StoringParser() {
			super(new NullSink());
		}

		public void pushSerial(char[] data, int offset, int count) {
			this.data = data;
			this.offset = offset;
			this.count = count;
		}

	}

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

	@Test
	public void pushCharArray() throws MalformedJSONException {
		StoringParser store = new StoringParser();
		char[] data = new char[16];
		store.pushSerial(data);
		assertSame(data, store.data);
		assertNotNull(store.offset);
		assertEquals(0, (int)store.offset);
		assertNotNull(store.count);
		assertEquals(data.length, (int)store.count);
	}

	@Test
	public void pushString() throws MalformedJSONException {
		StoringParser store = new StoringParser();
		String data = "hello, world!";
		store.pushSerial(data);
		assertNotNull(store.data);
		assertEquals(data.length(), store.data.length);
		for(int i = 0; i < store.data.length; ++i)
			assertEquals("character data at index " + i, data.charAt(i), store.data[i]);
		assertNotNull(store.offset);
		assertEquals(0, (int)store.offset);
		assertNotNull(store.count);
		assertEquals(data.length(), (int)store.count);
	}

	@Test
	public void pushStringSlice() throws MalformedJSONException {
		StoringParser store = new StoringParser();
		String data = "hello, world! how are you?";
		int offset = data.length() / 3, count = data.length() / 2;
		store.pushSerial(data, offset, count);
		assertNotNull(store.data);
		assertEquals(data.length(), store.data.length);
		for(int i = 0; i < store.data.length; ++i)
			assertEquals("character data at index " + i, data.charAt(i), store.data[i]);
		assertNotNull(store.offset);
		assertEquals(offset, (int)store.offset);
		assertNotNull(store.count);
		assertEquals(count, (int)store.count);
	}

	@Test
	public void beforeDocumentLine() throws MalformedJSONException {
		try {
			new JSONParser(new NullSink()).pushSerial(" }");
			fail("invalid document parsed");
		}
		catch(MalformedJSONException mje) {
			assertEquals(1, mje.getLineNumber());
		}
		try {
			new JSONParser(new NullSink()).pushSerial(" \n }");
			fail("invalid document parsed");
		}
		catch(MalformedJSONException mje) {
			assertEquals(2, mje.getLineNumber());
		}
	}

	@Test
	public void topObjectPop() throws MalformedJSONException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("{}");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[1].getKind());
	}

	@Test
	public void topArrayPop() throws MalformedJSONException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[1].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void topString() throws MalformedJSONException {
		new JSONParser(new NullSink()).pushSerial("\"foo\"");
	}

	@Test(expected = MalformedJSONException.class)
	public void topNumber() throws MalformedJSONException {
		new JSONParser(new NullSink()).pushSerial("42");
	}

	@Test(expected = MalformedJSONException.class)
	public void topSign() throws MalformedJSONException {
		new JSONParser(new NullSink()).pushSerial("-42");
	}

	@Test(expected = MalformedJSONException.class)
	public void topTrue() throws MalformedJSONException {
		new JSONParser(new NullSink()).pushSerial("true");
	}

	@Test(expected = MalformedJSONException.class)
	public void topFalse() throws MalformedJSONException {
		new JSONParser(new NullSink()).pushSerial("false");
	}

	@Test
	public void plainString() throws MalformedJSONException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[\"hello\",");
		parser.pushSerial("\"w");
		parser.pushSerial("orld\"");
		parser.pushSerial(",\"");
		parser.pushSerial("\",\"\"]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(6, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("hello", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[2].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[2]).value);
		assertEquals("world", ((StoringSink.FoundStringCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[4].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[4]).value);
		assertEquals("", ((StoringSink.FoundStringCall)calls[4]).value);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[5].getKind());
	}

	@Test
	public void stringEscapes() throws MalformedJSONException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[\"a\\\\b\\/cd\",\"\\\"\",\"\\b\\f\\n\\r\\t\",\"\\");
		parser.pushSerial("n\"]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(6, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("a\\b/cd", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[2].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[2]).value);
		assertEquals("\"", ((StoringSink.FoundStringCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("\b\f\n\r\t", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[4].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[4]).value);
		assertEquals("\n", ((StoringSink.FoundStringCall)calls[4]).value);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[5].getKind());
	}

}
