package org.unclesniper.json;

import java.io.Writer;
import java.util.Deque;
import java.io.IOException;
import java.util.ArrayDeque;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class JSONPrinter implements JSONSink {

	private enum Enclosing {

		EMPTY_OBJECT(JSON.TYPE_OBJECT),
		NONEMPTY_OBJECT(JSON.TYPE_OBJECT),
		EMPTY_ARRAY(JSON.TYPE_ARRAY),
		SIMPLE_ARRAY(JSON.TYPE_ARRAY),
		COMPLEX_ARRAY(JSON.TYPE_ARRAY);

		final int type;

		private Enclosing(int type) {
			this.type = type;
		}

	}

	public static final String DEFAULT_INDENT = "\t";

	public static final String DEFAULT_LINE_BREAK = System.getProperty("line.separator");

	private static final char[] trueChars = "true".toCharArray();

	private static final char[] falseChars = "false".toCharArray();

	private static final char[] nullChars = "null".toCharArray();

	private static final char[] backspaceChars = "\\b".toCharArray();

	private static final char[] formfeedChars = "\\f".toCharArray();

	private static final char[] newlineChars = "\\n".toCharArray();

	private static final char[] carriageReturnChars = "\\r".toCharArray();

	private static final char[] tabChars = "\\t".toCharArray();

	private static final char[] unicodeChars = "\\u".toCharArray();

	private static final char[] hexChars = "0123456789ABCDEF".toCharArray();

	private Writer out;

	private String indentString = JSONPrinter.DEFAULT_INDENT;

	private String lineBreakString = JSONPrinter.DEFAULT_LINE_BREAK;

	private boolean pretty;

	private Deque<Enclosing> stack = new ArrayDeque<Enclosing>();

	private boolean haveKey;

	public JSONPrinter(Writer out) {
		this.out = out;
	}

	public JSONPrinter(OutputStream out) throws IOException {
		this(new OutputStreamWriter(out, "UTF-8"));
	}

	public JSONPrinter(OutputStream out, String charset) throws IOException {
		this(new OutputStreamWriter(out, charset == null ? "UTF-8" : charset));
	}

	public String getIndentString() {
		return indentString;
	}

	public void setIndentString(String indentString) {
		this.indentString = indentString == null ? JSONPrinter.DEFAULT_INDENT : indentString;
	}

	public String getLineBreakString() {
		return lineBreakString;
	}

	public void setLineBreakString(String lineBreakString) {
		this.lineBreakString = lineBreakString == null ? JSONPrinter.DEFAULT_LINE_BREAK : lineBreakString;
	}

	public boolean isPrettyPrinter() {
		return pretty;
	}

	public void setPrettyPrinter(boolean pretty) {
		this.pretty = pretty;
	}

	private void formatEnclosing(boolean complex) throws IOException {
		if(stack.isEmpty())
			return;
		switch(stack.getLast()) {
			case EMPTY_OBJECT:
				stack.removeLast();
				stack.addLast(Enclosing.NONEMPTY_OBJECT);
				if(pretty) {
					out.write(lineBreakString);
					for(int count = stack.size(); count > 0; --count)
						out.write(indentString);
				}
				haveKey = !haveKey;
				break;
			case NONEMPTY_OBJECT:
				if(haveKey) {
					out.write(':');
					haveKey = false;
					if(pretty)
						out.write(' ');
				}
				else {
					out.write(',');
					haveKey = true;
					if(pretty) {
						out.write(lineBreakString);
						for(int count = stack.size(); count > 0; --count)
							out.write(indentString);
					}
				}
				break;
			case EMPTY_ARRAY:
				stack.removeLast();
				stack.addLast(complex ? Enclosing.COMPLEX_ARRAY : Enclosing.SIMPLE_ARRAY);
				if(pretty && complex) {
					out.write(lineBreakString);
					for(int count = stack.size(); count > 0; --count)
						out.write(indentString);
				}
				break;
			case SIMPLE_ARRAY:
				if(pretty)
					out.write(", ");
				else
					out.write(',');
				break;
			case COMPLEX_ARRAY:
				out.write(',');
				if(pretty) {
					out.write(lineBreakString);
					for(int count = stack.size(); count > 0; --count)
						out.write(indentString);
				}
				break;
			default:
				throw new OMGWereDoomedError("Unrecognized enclosing value: " + stack.getLast().name());
		}
	}

	public void foundBoolean(boolean value) throws IOException {
		formatEnclosing(false);
		out.write(value ? JSONPrinter.trueChars : JSONPrinter.falseChars);
	}

	public void foundNull() throws IOException {
		formatEnclosing(false);
		out.write(JSONPrinter.nullChars);
	}

	public void foundString(String value) throws IOException {
		formatEnclosing(false);
		out.write('"');
		int i, length = value.length();
		char c;
		for(i = 0; i < length; ++i)
			switch(c = value.charAt(i)) {
				case '"':
				case '\\':
					out.write('\\');
					out.write(c);
					break;
				case '\b':
					out.write(JSONPrinter.backspaceChars);
					break;
				case '\f':
					out.write(JSONPrinter.formfeedChars);
					break;
				case '\n':
					out.write(JSONPrinter.newlineChars);
					break;
				case '\r':
					out.write(JSONPrinter.carriageReturnChars);
					break;
				case '\t':
					out.write(JSONPrinter.tabChars);
					break;
				default:
					if(c < ' ') {
						out.write(unicodeChars);
						int code = (int)c;
						for(i = 12; i >= 0; i -= 4)
							out.write(JSONPrinter.hexChars[(code >> i) & 0xF]);
					}
					else
						out.write(c);
					break;
			}
		out.write('"');
	}

	public void foundInteger(long value) throws IOException {
		formatEnclosing(false);
		out.write(String.valueOf(value));
	}

	public void foundFraction(double value) throws IOException {
		formatEnclosing(false);
		out.write(String.valueOf(value));
	}

	public void beginObject() throws IOException {
		formatEnclosing(true);
		out.write('{');
		stack.addLast(Enclosing.EMPTY_OBJECT);
		haveKey = false;
	}

	public void endObject() throws IOException {
		if(stack.removeLast().type != JSON.TYPE_OBJECT)
			throw new IllegalStateException("Cannot end JSON object, top of stack is an array");
		if(pretty) {
			out.write(lineBreakString);
			for(int count = stack.size(); count > 0; --count)
				out.write(indentString);
		}
		out.write('}');
		if(stack.isEmpty())
			out.flush();
		haveKey = false;
	}

	public void beginArray() throws IOException {
		formatEnclosing(true);
		out.write('[');
		stack.addLast(Enclosing.EMPTY_ARRAY);
	}

	public void endArray() throws IOException {
		switch(stack.removeLast()) {
			case COMPLEX_ARRAY:
				if(pretty) {
					out.write(lineBreakString);
					for(int count = stack.size(); count > 0; --count)
						out.write(indentString);
				}
			case EMPTY_ARRAY:
			case SIMPLE_ARRAY:
				out.write(']');
				break;
			default:
				throw new IllegalStateException("Cannot end JSON array, top of stack is an object");
		}
		if(stack.isEmpty())
			out.flush();
		haveKey = false;
	}

}
