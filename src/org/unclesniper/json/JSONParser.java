package org.unclesniper.json;

import java.util.Deque;
import java.util.ArrayDeque;

public class JSONParser {

	private enum State {

		BEFORE_DOCUMENT,
		NONE,
		NAME_F,
		NAME_FA,
		NAME_FAL,
		NAME_FLAS,
		NAME_FLASE,
		NAME_T,
		NAME_TR,
		NAME_TRU,
		NAME_TRUE,
		NAME_N,
		NAME_NU,
		NAME_NUL,
		NAME_NULL,
		BEFORE_INITIAL_KEY,
		BEFORE_KEY,
		BEFORE_NAME_SEPARATOR,
		BEFORE_VALUE,
		BEFORE_MEMBER_SEPARATOR,
		BEFORE_INITIAL_ELEMENT,
		BEFORE_ELEMENT,
		BEFORE_ELEMENT_SEPARATOR,
		STRING,
		STRING_ESCAPE,
		STRING_UNICODE,
		AFTER_DOCUMENT;

	}

	private JSONSink sink;

	private State state = State.BEFORE_DOCUMENT;

	private Deque<State> stack = new ArrayDeque<State>();

	private StringBuilder string;

	private int line = 1;

	private int code;

	private int digits;

	public JSONParser(JSONSink sink) {
		if(sink == null)
			throw new NullPointerException("Sink cannot be null");
		this.sink = sink;
	}

	void reset() {
		state = State.BEFORE_DOCUMENT;
		stack.clear();
		string = null;
		line = 1;
	}

	public void pushSerial(char[] data) throws MalformedJSONException {
		pushSerial(data, 0, data.length);
	}

	public void pushSerial(char[] data, int offset, int count) throws MalformedJSONException {
		int start = offset;
		for(; offset < count; ++offset) {
			char c = data[offset];
			switch(state) {
				case BEFORE_DOCUMENT:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case '{':
							state = State.BEFORE_INITIAL_KEY;
							sink.beginObject();
							break;
						case '[':
							state = State.BEFORE_INITIAL_ELEMENT;
							sink.beginArray();
							break;
						default:
							throw new MalformedJSONException("Unexpected character in line " + line
									+ "; document must start with '[' or '{': code " + (int)c, line);
					}
					break;
				case NONE:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case 'f':
							state = State.NAME_F;
							break;
						case 'n':
							state = State.NAME_N;
							break;
						case 't':
							state = State.NAME_T;
							break;
						case '{':
							state = State.BEFORE_INITIAL_KEY;
							sink.beginObject();
							break;
						case '[':
							state = State.BEFORE_INITIAL_ELEMENT;
							sink.beginArray();
							break;
						case '"':
							start = offset + 1;
							state = State.STRING;
							break;
						//TODO: number
						default:
							throw new MalformedJSONException("Expected value in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case STRING:
					switch(c) {
						case '"':
							state = stack.removeLast();
							if(string == null)
								sink.foundString(String.valueOf(data, start, offset - start));
							else {
								string.append(data, start, offset - start);
								String s = string.toString();
								string = null;
								sink.foundString(s);
							}
							break;
						case '\\':
							if(string == null)
								string = new StringBuilder();
							string.append(data, start, offset);
							state = State.STRING_ESCAPE;
							break;
						default:
							if(c < ' ')
								throw new MalformedJSONException("Unescaped control character in string in line "
										+ line + ": code " + (int)c, line);
							break;
					}
					break;
				case STRING_ESCAPE:
					switch(c) {
						case '"':
						case '\\':
						case '/':
							string.append(c);
							start = offset + 1;
							state = State.STRING;
							break;
						case 'b':
							string.append('\b');
							start = offset + 1;
							state = State.STRING;
							break;
						case 'f':
							string.append('\f');
							start = offset + 1;
							state = State.STRING;
							break;
						case 'n':
							string.append('\n');
							start = offset + 1;
							state = State.STRING;
							break;
						case 'r':
							string.append('\r');
							start = offset + 1;
							state = State.STRING;
							break;
						case 't':
							string.append('\t');
							start = offset + 1;
							state = State.STRING;
							break;
						case 'u':
							digits = code = 0;
							state = State.STRING_UNICODE;
							break;
						default:
							throw new MalformedJSONException("Escape symbol must be followed by one of '\"', "
									+ "'\\', '/', 'b', 'f', 'n', 'r', 't' or 'u' in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case STRING_UNICODE:
					if(c >= '0' && c <= '9')
						code = code * 16 + (c - '0');
					else if(c >= 'a' && c <= 'f')
						code = code * 16 + (c - 'a') + 10;
					else if(c >= 'A' && c <= 'F')
						code = code * 16 + (c - 'A') + 10;
					else
						throw new MalformedJSONException("Unicode escape (\\uXXXX) must be specified by "
								+ "hexadecimal digits in line " + line + ", not code " + (int)c, line);
					if(++digits == 4) {
						string.append((char)code);
						start = offset + 1;
						state = State.STRING;
					}
					break;
				case BEFORE_INITIAL_KEY:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case '}':
							if(stack.isEmpty())
								state = State.AFTER_DOCUMENT;
							else
								state = stack.removeLast();
							sink.endObject();
						case '"':
							start = offset + 1;
							state = State.STRING;
							stack.addLast(State.BEFORE_NAME_SEPARATOR);
							break;
						default:
							throw new MalformedJSONException("Expected '\"' to start object member or '}' "
									+ "to end object in line " + line + ", not code " + (int)c, line);
					}
					break;
				case BEFORE_KEY:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case '"':
							start = offset + 1;
							state = State.STRING;
							stack.addLast(State.BEFORE_NAME_SEPARATOR);
							break;
						default:
							throw new MalformedJSONException("Expected '\"' to start object member in line "
									+ line + ", not code " + (int)c, line);
					}
					break;
				case BEFORE_NAME_SEPARATOR:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case ':':
							state = State.BEFORE_VALUE;
							break;
						default:
							throw new MalformedJSONException("Expected ':' to separate property name of "
									+ "object member from property value in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_VALUE:
					//TODO: like NONE, but push BEFORE_MEMBER_SEPARATOR appropriately
					break;
				case BEFORE_INITIAL_ELEMENT:
					//TODO: like NONE, but push BEFORE_MEMBER_SEPARATOR appropriately and expect ']'
					break;
				case BEFORE_ELEMENT:
					//TODO: like NONE, but push BEFORE_MEMBER_SEPARATOR appropriately
					break;
			}
		}
	}

}
