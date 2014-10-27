package org.unclesniper.json;

import java.io.File;
import java.io.Reader;
import java.util.Deque;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JSONBuilder implements JSONSink {

	private JSON root;

	private Deque<JSON> stack = new ArrayDeque<JSON>();

	private String key;

	public JSON getRoot() {
		if(!stack.isEmpty())
			throw new IllegalStateException("Cannot obtain object tree; construction is not finished");
		return root;
	}

	public void foundBoolean(boolean value) {
		if(key == null)
			stack.getLast().takeElement(new JSONBoolean(value));
		else {
			stack.getLast().takePair(key, new JSONBoolean(value));
			key = null;
		}
	}

	public void foundNull() {
		if(key == null)
			stack.getLast().takeElement(JSONNull.instance);
		else {
			stack.getLast().takePair(key, JSONNull.instance);
			key = null;
		}
	}

	public void foundString(String value) {
		if(key != null) {
			stack.getLast().takePair(key, new JSONString(value));
			key = null;
		}
		else {
			JSON top = stack.getLast();
			if(top.takesPairs())
				key = value;
			else
				top.takeElement(new JSONString(value));
		}
	}

	public void foundInteger(int value) {
		if(key == null)
			stack.getLast().takeElement(new JSONInteger(value));
		else {
			stack.getLast().takePair(key, new JSONInteger(value));
			key = null;
		}
	}

	public void foundFraction(double value) {
		if(key == null)
			stack.getLast().takeElement(new JSONFraction(value));
		else {
			stack.getLast().takePair(key, new JSONFraction(value));
			key = null;
		}
	}

	public void beginObject() {
		JSONObject object = new JSONObject();
		if(root == null)
			root = object;
		else if(key == null)
			stack.getLast().takeElement(object);
		else {
			stack.getLast().takePair(key, object);
			key = null;
		}
		stack.addLast(object);
	}

	public void endObject() {
		if(stack.removeLast().getJSONType() != JSON.TYPE_OBJECT)
			throw new IllegalStateException("Cannot end JSON object, top of stack is an array");
	}

	public void beginArray() {
		JSONArray array = new JSONArray();
		if(root == null)
			root = array;
		else if(key == null)
			stack.getLast().takeElement(array);
		else {
			stack.getLast().takePair(key, array);
			key = null;
		}
		stack.addLast(array);
	}

	public void endArray() {
		if(stack.removeLast().getJSONType() != JSON.TYPE_ARRAY)
			throw new IllegalStateException("Cannot end JSON array, top of stack is an object");
	}

	public static JSON readFrom(String data) throws MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		JSONParser parser = new JSONParser(builder);
		try {
			parser.pushSerial(data.toCharArray(), 0, data.length());
		}
		catch(IOException ioe) {
			throw new OMGWereDoomedError(ioe.getMessage(), ioe);
		}
		parser.endDocument();
		return builder.getRoot();
	}

	public static JSON readFrom(String data, int offset, int count) throws MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		JSONParser parser = new JSONParser(builder);
		try {
			parser.pushSerial(data.toCharArray(), offset, count);
		}
		catch(IOException ioe) {
			throw new OMGWereDoomedError(ioe.getMessage(), ioe);
		}
		parser.endDocument();
		return builder.getRoot();
	}

	public static JSON readFrom(char[] data) throws MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		JSONParser parser = new JSONParser(builder);
		try {
			parser.pushSerial(data, 0, data.length);
		}
		catch(IOException ioe) {
			throw new OMGWereDoomedError(ioe.getMessage(), ioe);
		}
		parser.endDocument();
		return builder.getRoot();
	}

	public static JSON readFrom(char[] data, int offset, int count) throws MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		JSONParser parser = new JSONParser(builder);
		try {
			parser.pushSerial(data, offset, count);
		}
		catch(IOException ioe) {
			throw new OMGWereDoomedError(ioe.getMessage(), ioe);
		}
		parser.endDocument();
		return builder.getRoot();
	}

	public static JSON readFrom(Reader in) throws IOException, MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		new JSONParser(builder).pullSerial(in);
		return builder.getRoot();
	}

	public static JSON readFrom(InputStream in) throws IOException, MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		new JSONParser(builder).pullSerial(new InputStreamReader(in, "UTF-8"));
		return builder.getRoot();
	}

	public static JSON readFrom(InputStream in, String charset) throws IOException, MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		new JSONParser(builder).pullSerial(new InputStreamReader(in, charset == null ? "UTF-8" : charset));
		return builder.getRoot();
	}

	public static JSON readFrom(File file) throws IOException, MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		FileInputStream fis = new FileInputStream(file);
		try {
			new JSONParser(builder).pullSerial(new InputStreamReader(fis, "UTF-8"));
		}
		finally {
			fis.close();
		}
		return builder.getRoot();
	}

	public static JSON readFrom(File file, String charset) throws IOException, MalformedJSONException {
		JSONBuilder builder = new JSONBuilder();
		FileInputStream fis = new FileInputStream(file);
		try {
			new JSONParser(builder).pullSerial(new InputStreamReader(fis, charset == null ? "UTF-8" : charset));
		}
		finally {
			fis.close();
		}
		return builder.getRoot();
	}

}
