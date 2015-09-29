package org.unclesniper.json;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JSONBuilderTests {

	@Test
	public void simpleArray() {
		JSONBuilder builder = new JSONBuilder();
		builder.beginArray();
		builder.foundBoolean(true);
		builder.foundNull();
		builder.foundString("foo");
		builder.foundInteger(42l);
		builder.foundFraction(3.141);
		builder.endArray();
		JSON root = builder.getRoot();
		assertTrue(root instanceof JSONArray);
		JSONArray array = (JSONArray)root;
		assertEquals(5, array.size());
		assertTrue(array.get(0) instanceof JSONBoolean);
		assertTrue(((JSONBoolean)array.get(0)).booleanValue());
		assertTrue(array.get(1) instanceof JSONNull);
		assertTrue(array.get(2) instanceof JSONString);
		assertNotNull(((JSONString)array.get(2)).stringValue());
		assertEquals("foo", ((JSONString)array.get(2)).stringValue());
		assertTrue(array.get(3) instanceof JSONInteger);
		assertEquals(42l, ((JSONInteger)array.get(3)).longValue());
		assertTrue(array.get(4) instanceof JSONFraction);
		assertEquals(3.141, ((JSONFraction)array.get(4)).doubleValue(), 0.0001);
	}

	@Test
	public void simpleObject() {
		JSONBuilder builder = new JSONBuilder();
		builder.beginObject();
		builder.foundString("bool");
		builder.foundBoolean(false);
		builder.foundString("nothing");
		builder.foundNull();
		builder.foundString("str");
		builder.foundString("hello");
		builder.foundString("int");
		builder.foundInteger(123l);
		builder.foundString("double");
		builder.foundFraction(6.5);
		builder.endObject();
		JSON root = builder.getRoot();
		assertTrue(root instanceof JSONObject);
		JSONObject object = (JSONObject)root;
		assertEquals(5, object.size());
		assertTrue(object.containsKey("bool"));
		assertTrue(object.get("bool") instanceof JSONBoolean);
		assertFalse(((JSONBoolean)object.get("bool")).booleanValue());
		assertTrue(object.containsKey("nothing"));
		assertTrue(object.get("nothing") instanceof JSONNull);
		assertTrue(object.containsKey("str"));
		assertTrue(object.get("str") instanceof JSONString);
		assertNotNull(((JSONString)object.get("str")).stringValue());
		assertEquals("hello", ((JSONString)object.get("str")).stringValue());
		assertTrue(object.containsKey("int"));
		assertTrue(object.get("int") instanceof JSONInteger);
		assertEquals(123l, ((JSONInteger)object.get("int")).longValue());
		assertTrue(object.containsKey("double"));
		assertTrue(object.get("double") instanceof JSONFraction);
		assertEquals(6.5, ((JSONFraction)object.get("double")).doubleValue(), 0.01);
	}

}
