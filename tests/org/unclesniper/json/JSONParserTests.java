package org.unclesniper.json;

import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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
	public void emptyDocument() throws MalformedJSONException, IOException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial(new char[0], 0, 0);
		parser.endDocument();
	}

	@Test
	public void pushCharArray() throws MalformedJSONException, IOException {
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
	public void pushString() throws MalformedJSONException, IOException {
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
	public void pushStringSlice() throws MalformedJSONException, IOException {
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
	public void beforeDocumentLine() throws MalformedJSONException, IOException {
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
	public void topObjectPop() throws MalformedJSONException, IOException {
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
	public void topArrayPop() throws MalformedJSONException, IOException {
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
	public void topString() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("\"foo\"");
	}

	@Test(expected = MalformedJSONException.class)
	public void topNumber() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("42");
	}

	@Test(expected = MalformedJSONException.class)
	public void topSign() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("-42");
	}

	@Test(expected = MalformedJSONException.class)
	public void topTrue() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("true");
	}

	@Test(expected = MalformedJSONException.class)
	public void topFalse() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("false");
	}

	@Test(expected = MalformedJSONException.class)
	public void topNull() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("null");
	}

	@Test
	public void plainString() throws MalformedJSONException, IOException {
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
	public void stringEscapes() throws MalformedJSONException, IOException {
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

	@Test
	public void stringUnicode() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[\"\\u12AB5678\",\"\\u");
		parser.pushSerial("1234");
		parser.pushSerial("\\u5");
		parser.pushSerial("678\"]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(4, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("\u12AB" + "5678", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[2].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[2]).value);
		assertEquals("\u1234\u5678", ((StoringSink.FoundStringCall)calls[2]).value);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[3].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void stringShortUnicode() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[\"\\u12x\"]");
	}

	@Test(expected = MalformedJSONException.class)
	public void stringControlChar() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[\"\n\"]");
	}

	@Test
	public void number() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[12,34.5,0,0.5,4,321,8.125,3e5,5E-3,6e+6,1.2e-5]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(13, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[1].getKind());
		assertEquals(12l, ((StoringSink.FoundIntegerCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[2].getKind());
		assertEquals(34.5, ((StoringSink.FoundFractionCall)calls[2]).value, 0.01);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
		assertEquals(0l, ((StoringSink.FoundIntegerCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[4].getKind());
		assertEquals(0.5, ((StoringSink.FoundFractionCall)calls[4]).value, 0.01);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[5].getKind());
		assertEquals(4l, ((StoringSink.FoundIntegerCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[6].getKind());
		assertEquals(321l, ((StoringSink.FoundIntegerCall)calls[6]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[7].getKind());
		assertEquals(8.125, ((StoringSink.FoundFractionCall)calls[7]).value, 0.0001);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[8].getKind());
		assertEquals(300000.0, ((StoringSink.FoundFractionCall)calls[8]).value, 0.1);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[9].getKind());
		assertEquals(0.005, ((StoringSink.FoundFractionCall)calls[9]).value, 0.0001);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[10].getKind());
		assertEquals(6000000.0, ((StoringSink.FoundFractionCall)calls[10]).value, 0.1);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[11].getKind());
		assertEquals(0.000012, ((StoringSink.FoundFractionCall)calls[11]).value, 0.0000001);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[12].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("[1");
		parser.pushSerial("2,34");
		parser.pushSerial(".5,0");
		parser.pushSerial(",0.");
		parser.pushSerial("5,4");
		parser.pushSerial(",");
		parser.pushSerial("321,");
		parser.pushSerial("8.");
		parser.pushSerial("1");
		parser.pushSerial("25");
		parser.pushSerial(",3");
		parser.pushSerial("e5");
		parser.pushSerial(",5E");
		parser.pushSerial("-3,");
		parser.pushSerial("6e");
		parser.pushSerial("+");
		parser.pushSerial("6,1.2");
		parser.pushSerial("e");
		parser.pushSerial("-5]");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(13, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[1].getKind());
		assertEquals(12l, ((StoringSink.FoundIntegerCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[2].getKind());
		assertEquals(34.5, ((StoringSink.FoundFractionCall)calls[2]).value, 0.01);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
		assertEquals(0l, ((StoringSink.FoundIntegerCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[4].getKind());
		assertEquals(0.5, ((StoringSink.FoundFractionCall)calls[4]).value, 0.01);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[5].getKind());
		assertEquals(4l, ((StoringSink.FoundIntegerCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[6].getKind());
		assertEquals(321l, ((StoringSink.FoundIntegerCall)calls[6]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[7].getKind());
		assertEquals(8.125, ((StoringSink.FoundFractionCall)calls[7]).value, 0.0001);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[8].getKind());
		assertEquals(300000.0, ((StoringSink.FoundFractionCall)calls[8]).value, 0.1);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[9].getKind());
		assertEquals(0.005, ((StoringSink.FoundFractionCall)calls[9]).value, 0.0001);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[10].getKind());
		assertEquals(6000000.0, ((StoringSink.FoundFractionCall)calls[10]).value, 0.1);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[11].getKind());
		assertEquals(0.000012, ((StoringSink.FoundFractionCall)calls[11]).value, 0.0000001);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[12].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void numberLeadingZero() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[01]");
	}

	@Test(expected = MalformedJSONException.class)
	public void numberPositive() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[+1]");
	}

	@Test(expected = MalformedJSONException.class)
	public void numberNoIntegral() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[.5]");
	}

	@Test(expected = MalformedJSONException.class)
	public void numberNoFractional() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[2.]");
	}

	@Test(expected = MalformedJSONException.class)
	public void numberNoExponent() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[2e]");
	}

	@Test
	public void emptyObject() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("{\t}");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[1].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("{");
		parser.pushSerial("}");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[1].getKind());
	}

	@Test
	public void objectProperties() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial(" { \"foo\" : 42 , \"bar\":\"baz\" } ");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(6, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("foo", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[2].getKind());
		assertEquals(42l, ((StoringSink.FoundIntegerCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("bar", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[4].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[4]).value);
		assertEquals("baz", ((StoringSink.FoundStringCall)calls[4]).value);
		assertSame(StoringSink.CallKind.END_OBJECT, calls[5].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("{");
		parser.pushSerial("\"foo\"");
		parser.pushSerial(":");
		parser.pushSerial("42");
		parser.pushSerial(",");
		parser.pushSerial("\"bar\"");
		parser.pushSerial(":");
		parser.pushSerial("\"baz\"");
		parser.pushSerial("}");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(6, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("foo", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[2].getKind());
		assertEquals(42l, ((StoringSink.FoundIntegerCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("bar", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[4].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[4]).value);
		assertEquals("baz", ((StoringSink.FoundStringCall)calls[4]).value);
		assertSame(StoringSink.CallKind.END_OBJECT, calls[5].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void leadingMemberSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{,\"a\":\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void trailingMemberSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{\"a\":\"b\",}");
	}

	@Test(expected = MalformedJSONException.class)
	public void objectNoValue() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{\"a\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void objectNoNameSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{\"a\" \"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void objectNoMemberSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{\"a\":\"b\" \"c\":\"d\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void leadingNameSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void trailingNameSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{\"b\":}");
	}

	@Test(expected = MalformedJSONException.class)
	public void numberKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{42:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void signKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{-42:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void arrayKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{[]:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void objectKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{{}:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void trueKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{true:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void falseKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{false:\"b\"}");
	}

	@Test(expected = MalformedJSONException.class)
	public void nullKey() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("{null:\"b\"}");
	}

	@Test
	public void emptyArray() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[\t]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[1].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("[");
		parser.pushSerial("]");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(2, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[1].getKind());
	}

	@Test
	public void arrayElements() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[ 42 , \"foo\",12.5 ]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(5, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[1].getKind());
		assertEquals(42l, ((StoringSink.FoundIntegerCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[2].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[2]).value);
		assertEquals("foo", ((StoringSink.FoundStringCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[3].getKind());
		assertEquals(12.5, ((StoringSink.FoundFractionCall)calls[3]).value, 0.01);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[4].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("[");
		parser.pushSerial("42");
		parser.pushSerial(",");
		parser.pushSerial("\"foo\"");
		parser.pushSerial(",");
		parser.pushSerial("12.5");
		parser.pushSerial("]");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(5, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[1].getKind());
		assertEquals(42l, ((StoringSink.FoundIntegerCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[2].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[2]).value);
		assertEquals("foo", ((StoringSink.FoundStringCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_FRACTION, calls[3].getKind());
		assertEquals(12.5, ((StoringSink.FoundFractionCall)calls[3]).value, 0.01);
		assertSame(StoringSink.CallKind.END_ARRAY, calls[4].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void leadingElementSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[,5]");
	}

	@Test(expected = MalformedJSONException.class)
	public void trailingElementSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[5,]");
	}

	@Test(expected = MalformedJSONException.class)
	public void arrayNoElementSeparator() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[5 6]");
	}

	@Test
	public void constantsInArray() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("[true,false,null, true , false , null ]");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(8, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[1].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[2].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[3].getKind());
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[4].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[4]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[5].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[6].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[7].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("[t");
		parser.pushSerial("ru");
		parser.pushSerial("e");
		parser.pushSerial(",f");
		parser.pushSerial("als");
		parser.pushSerial("e");
		parser.pushSerial(",n");
		parser.pushSerial("ul");
		parser.pushSerial("l");
		parser.pushSerial(",");
		parser.pushSerial("true");
		parser.pushSerial(",");
		parser.pushSerial("false");
		parser.pushSerial(",");
		parser.pushSerial("null");
		parser.pushSerial("]");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(8, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[1].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[2].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[3].getKind());
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[4].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[4]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[5].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[6].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[7].getKind());
	}

	@Test
	public void constantsInObject() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial("{\"a\":true,\"b\":false,\"c\":null,\"d\": true ,\"e\" : false ,\"f\" : null }");
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(14, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("a", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[2].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("b", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[4].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[4]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[5].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[5]).value);
		assertEquals("c", ((StoringSink.FoundStringCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[6].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[7]).value);
		assertEquals("d", ((StoringSink.FoundStringCall)calls[7]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[8].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[8]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[9].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[9]).value);
		assertEquals("e", ((StoringSink.FoundStringCall)calls[9]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[10].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[10]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[11].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[11]).value);
		assertEquals("f", ((StoringSink.FoundStringCall)calls[11]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[12].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[13].getKind());
		store = new StoringSink();
		parser = new JSONParser(store);
		parser.pushSerial("{\"a\":t");
		parser.pushSerial("ru");
		parser.pushSerial("e");
		parser.pushSerial(",\"b\":f");
		parser.pushSerial("als");
		parser.pushSerial("e");
		parser.pushSerial(",\"c\":n");
		parser.pushSerial("ul");
		parser.pushSerial("l");
		parser.pushSerial(",\"d\":");
		parser.pushSerial("true");
		parser.pushSerial(",\"e\":");
		parser.pushSerial("false");
		parser.pushSerial(",\"f\":");
		parser.pushSerial("null");
		parser.pushSerial("}");
		parser.endDocument();
		calls = store.getCalls();
		assertEquals(14, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
		assertEquals("a", ((StoringSink.FoundStringCall)calls[1]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[2].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[2]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[3].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[3]).value);
		assertEquals("b", ((StoringSink.FoundStringCall)calls[3]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[4].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[4]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[5].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[5]).value);
		assertEquals("c", ((StoringSink.FoundStringCall)calls[5]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[6].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[7]).value);
		assertEquals("d", ((StoringSink.FoundStringCall)calls[7]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[8].getKind());
		assertTrue(((StoringSink.FoundBooleanCall)calls[8]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[9].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[9]).value);
		assertEquals("e", ((StoringSink.FoundStringCall)calls[9]).value);
		assertSame(StoringSink.CallKind.FOUND_BOOLEAN, calls[10].getKind());
		assertFalse(((StoringSink.FoundBooleanCall)calls[10]).value);
		assertSame(StoringSink.CallKind.FOUND_STRING, calls[11].getKind());
		assertNotNull(((StoringSink.FoundStringCall)calls[11]).value);
		assertEquals("f", ((StoringSink.FoundStringCall)calls[11]).value);
		assertSame(StoringSink.CallKind.FOUND_NULL, calls[12].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[13].getKind());
	}

	@Test
	public void nestingArray() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial(
			"[\n" +
				"[1,2,3],\n" +
				"[[5],[6],[7]],\n" +
				"{\"a\":1,\"b\":2,\"c\":3},\n" +
				"{\"a\":[1],\"b\":[2],\"c\":[3]}\n" +
			"]"
		);
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(40, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[1].getKind());
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[2].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[2]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[3]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[4].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[4]).value);
			assertSame(StoringSink.CallKind.END_ARRAY, calls[5].getKind());
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[6].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[7].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[8].getKind());
					assertEquals(5l, ((StoringSink.FoundIntegerCall)calls[8]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[9].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[10].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[11].getKind());
					assertEquals(6l, ((StoringSink.FoundIntegerCall)calls[11]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[12].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[13].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[14].getKind());
					assertEquals(7l, ((StoringSink.FoundIntegerCall)calls[14]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[15].getKind());
			assertSame(StoringSink.CallKind.END_ARRAY, calls[16].getKind());
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[17].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[18].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[18]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[18]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[19].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[19]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[20].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[20]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[20]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[21].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[21]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[22].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[22]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[22]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[23].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[23]).value);
			assertSame(StoringSink.CallKind.END_OBJECT, calls[24].getKind());
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[25].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[26].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[26]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[26]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[27].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[28].getKind());
					assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[28]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[29].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[30].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[30]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[30]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[31].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[32].getKind());
					assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[32]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[33].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[34].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[34]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[34]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[35].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[36].getKind());
					assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[36]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[37].getKind());
			assertSame(StoringSink.CallKind.END_OBJECT, calls[38].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[39].getKind());
	}

	@Test
	public void nestingArrayChars() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		String data =
			"[\n" +
				"[1,2,3],\n" +
				"[[5],[6],[7]],\n" +
				"{\"a\":1,\"b\":2,\"c\":3},\n" +
				"{\"a\":[1],\"b\":[2],\"c\":[3]}\n" +
			"]";
		for(int i = 0; i < data.length(); ++i)
			parser.pushSerial(data.substring(i, i + 1));
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(40, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[0].getKind());
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[1].getKind());
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[2].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[2]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[3]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[4].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[4]).value);
			assertSame(StoringSink.CallKind.END_ARRAY, calls[5].getKind());
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[6].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[7].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[8].getKind());
					assertEquals(5l, ((StoringSink.FoundIntegerCall)calls[8]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[9].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[10].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[11].getKind());
					assertEquals(6l, ((StoringSink.FoundIntegerCall)calls[11]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[12].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[13].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[14].getKind());
					assertEquals(7l, ((StoringSink.FoundIntegerCall)calls[14]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[15].getKind());
			assertSame(StoringSink.CallKind.END_ARRAY, calls[16].getKind());
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[17].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[18].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[18]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[18]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[19].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[19]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[20].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[20]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[20]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[21].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[21]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[22].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[22]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[22]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[23].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[23]).value);
			assertSame(StoringSink.CallKind.END_OBJECT, calls[24].getKind());
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[25].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[26].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[26]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[26]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[27].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[28].getKind());
					assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[28]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[29].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[30].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[30]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[30]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[31].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[32].getKind());
					assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[32]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[33].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[34].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[34]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[34]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[35].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[36].getKind());
					assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[36]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[37].getKind());
			assertSame(StoringSink.CallKind.END_OBJECT, calls[38].getKind());
		assertSame(StoringSink.CallKind.END_ARRAY, calls[39].getKind());
	}

	@Test
	public void nestingObject() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		parser.pushSerial(
			"{\n" +
				"\"shallowArray\":[1,2,3],\n" +
				"\"deepArray\":[[5],[6],[7]],\n" +
				"\"shallowObject\":{\"a\":1,\"b\":2,\"c\":3},\n" +
				"\"deepObject\":{\"a\":[1],\"b\":[2],\"c\":[3]}\n" +
			"}"
		);
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(44, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
			assertEquals("shallowArray", ((StoringSink.FoundStringCall)calls[1]).value);
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[2].getKind());
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[3]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[4].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[4]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[5].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[5]).value);
			assertSame(StoringSink.CallKind.END_ARRAY, calls[6].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[7].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[7]).value);
			assertEquals("deepArray", ((StoringSink.FoundStringCall)calls[7]).value);
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[8].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[9].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[10].getKind());
					assertEquals(5l, ((StoringSink.FoundIntegerCall)calls[10]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[11].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[12].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[13].getKind());
					assertEquals(6l, ((StoringSink.FoundIntegerCall)calls[13]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[14].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[15].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[16].getKind());
					assertEquals(7l, ((StoringSink.FoundIntegerCall)calls[16]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[17].getKind());
			assertSame(StoringSink.CallKind.END_ARRAY, calls[18].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[19].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[19]).value);
			assertEquals("shallowObject", ((StoringSink.FoundStringCall)calls[19]).value);
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[20].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[21].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[21]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[21]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[22].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[22]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[23].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[23]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[23]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[24].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[24]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[25].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[25]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[25]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[26].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[26]).value);
			assertSame(StoringSink.CallKind.END_OBJECT, calls[27].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[28].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[28]).value);
			assertEquals("deepObject", ((StoringSink.FoundStringCall)calls[28]).value);
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[29].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[30].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[30]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[30]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[31].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[32].getKind());
					assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[32]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[33].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[34].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[34]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[34]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[35].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[36].getKind());
					assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[36]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[37].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[38].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[38]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[38]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[39].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[40].getKind());
					assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[40]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[41].getKind());
			assertSame(StoringSink.CallKind.END_OBJECT, calls[42].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[43].getKind());
	}

	@Test
	public void nestingObjectChars() throws MalformedJSONException, IOException {
		StoringSink store = new StoringSink();
		JSONParser parser = new JSONParser(store);
		String data =
			"{\n" +
				"\"shallowArray\":[1,2,3],\n" +
				"\"deepArray\":[[5],[6],[7]],\n" +
				"\"shallowObject\":{\"a\":1,\"b\":2,\"c\":3},\n" +
				"\"deepObject\":{\"a\":[1],\"b\":[2],\"c\":[3]}\n" +
			"}";
		for(int i = 0; i < data.length(); ++i)
			parser.pushSerial(data.substring(i, i + 1));
		parser.endDocument();
		StoringSink.Call[] calls = store.getCalls();
		assertEquals(44, calls.length);
		assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[0].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[1].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[1]).value);
			assertEquals("shallowArray", ((StoringSink.FoundStringCall)calls[1]).value);
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[2].getKind());
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[3].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[3]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[4].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[4]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[5].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[5]).value);
			assertSame(StoringSink.CallKind.END_ARRAY, calls[6].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[7].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[7]).value);
			assertEquals("deepArray", ((StoringSink.FoundStringCall)calls[7]).value);
			assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[8].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[9].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[10].getKind());
					assertEquals(5l, ((StoringSink.FoundIntegerCall)calls[10]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[11].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[12].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[13].getKind());
					assertEquals(6l, ((StoringSink.FoundIntegerCall)calls[13]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[14].getKind());
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[15].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[16].getKind());
					assertEquals(7l, ((StoringSink.FoundIntegerCall)calls[16]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[17].getKind());
			assertSame(StoringSink.CallKind.END_ARRAY, calls[18].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[19].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[19]).value);
			assertEquals("shallowObject", ((StoringSink.FoundStringCall)calls[19]).value);
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[20].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[21].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[21]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[21]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[22].getKind());
				assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[22]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[23].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[23]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[23]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[24].getKind());
				assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[24]).value);
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[25].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[25]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[25]).value);
				assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[26].getKind());
				assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[26]).value);
			assertSame(StoringSink.CallKind.END_OBJECT, calls[27].getKind());
			assertSame(StoringSink.CallKind.FOUND_STRING, calls[28].getKind());
			assertNotNull(((StoringSink.FoundStringCall)calls[28]).value);
			assertEquals("deepObject", ((StoringSink.FoundStringCall)calls[28]).value);
			assertSame(StoringSink.CallKind.BEGIN_OBJECT, calls[29].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[30].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[30]).value);
				assertEquals("a", ((StoringSink.FoundStringCall)calls[30]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[31].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[32].getKind());
					assertEquals(1l, ((StoringSink.FoundIntegerCall)calls[32]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[33].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[34].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[34]).value);
				assertEquals("b", ((StoringSink.FoundStringCall)calls[34]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[35].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[36].getKind());
					assertEquals(2l, ((StoringSink.FoundIntegerCall)calls[36]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[37].getKind());
				assertSame(StoringSink.CallKind.FOUND_STRING, calls[38].getKind());
				assertNotNull(((StoringSink.FoundStringCall)calls[38]).value);
				assertEquals("c", ((StoringSink.FoundStringCall)calls[38]).value);
				assertSame(StoringSink.CallKind.BEGIN_ARRAY, calls[39].getKind());
					assertSame(StoringSink.CallKind.FOUND_INTEGER, calls[40].getKind());
					assertEquals(3l, ((StoringSink.FoundIntegerCall)calls[40]).value);
				assertSame(StoringSink.CallKind.END_ARRAY, calls[41].getKind());
			assertSame(StoringSink.CallKind.END_OBJECT, calls[42].getKind());
		assertSame(StoringSink.CallKind.END_OBJECT, calls[43].getKind());
	}

	@Test(expected = MalformedJSONException.class)
	public void badNesting() throws MalformedJSONException, IOException {
		new JSONParser(new NullSink()).pushSerial("[}");
	}

	@Test(expected = MalformedJSONException.class)
	public void unclosedString() throws MalformedJSONException, IOException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial("[\"foo]");
		parser.endDocument();
	}

	@Test(expected = MalformedJSONException.class)
	public void unclosedEscape() throws MalformedJSONException, IOException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial("[\"foo\\");
		parser.endDocument();
	}

	@Test(expected = MalformedJSONException.class)
	public void unclosedArray() throws MalformedJSONException, IOException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial("[");
		parser.endDocument();
	}

	@Test(expected = MalformedJSONException.class)
	public void unclosedObject() throws MalformedJSONException, IOException {
		JSONParser parser = new JSONParser(new NullSink());
		parser.pushSerial("{");
		parser.endDocument();
	}

}
