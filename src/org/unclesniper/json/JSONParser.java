package org.unclesniper.json;

import java.io.File;
import java.io.Reader;
import java.util.Deque;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JSONParser {

	private enum State {

		BEFORE_DOCUMENT,
		NONE,
		NAME_F,
		NAME_FA,
		NAME_FAL,
		NAME_FALS,
		NAME_FALSE,
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
		BEFORE_INT,
		WITHIN_INT,
		AFTER_INT,
		BEFORE_FRACTION,
		WITHIN_FRACTION,
		BEFORE_EXPONENT_SIGN,
		BEFORE_EXPONENT,
		WITHIN_EXPONENT,
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

	public void pushSerial(String data) throws MalformedJSONException {
		pushSerial(data.toCharArray(), 0, data.length());
	}

	public void pushSerial(String data, int offset, int count) throws MalformedJSONException {
		pushSerial(data.toCharArray(), offset, count);
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
						case '-':
							start = offset;
							state = State.BEFORE_INT;
							break;
						case '0':
							start = offset;
							state = State.AFTER_INT;
							break;
						default:
							if(c >= '1' && c <= '9') {
								start = offset;
								state = State.WITHIN_INT;
								break;
							}
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
							string.append(data, start, offset - start);
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
							break;
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
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case 'f':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							state = State.NAME_F;
							break;
						case 'n':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							state = State.NAME_N;
							break;
						case 't':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							state = State.NAME_T;
							break;
						case '{':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							state = State.BEFORE_INITIAL_KEY;
							sink.beginObject();
							break;
						case '[':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							state = State.BEFORE_INITIAL_ELEMENT;
							sink.beginArray();
							break;
						case '"':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							start = offset + 1;
							state = State.STRING;
							break;
						case '-':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							start = offset;
							state = State.BEFORE_INT;
							break;
						case '0':
							stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
							start = offset;
							state = State.AFTER_INT;
							break;
						default:
							if(c >= '1' && c <= '9') {
								stack.addLast(State.BEFORE_MEMBER_SEPARATOR);
								start = offset;
								state = State.WITHIN_INT;
								break;
							}
							throw new MalformedJSONException("Expected value in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_MEMBER_SEPARATOR:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case ',':
							state = State.BEFORE_KEY;
							break;
						case '}':
							if(stack.isEmpty())
								state = State.AFTER_DOCUMENT;
							else
								state = stack.removeLast();
							sink.endObject();
							break;
						default:
							throw new MalformedJSONException("Expected ',' to separate "
									+ "object members or '}' to end object in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_INITIAL_ELEMENT:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case 'f':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_F;
							break;
						case 'n':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_N;
							break;
						case 't':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_T;
							break;
						case '{':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.BEFORE_INITIAL_KEY;
							sink.beginObject();
							break;
						case '[':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.BEFORE_INITIAL_ELEMENT;
							sink.beginArray();
							break;
						case ']':
							if(stack.isEmpty())
								state = State.AFTER_DOCUMENT;
							else
								state = stack.removeLast();
							sink.endArray();
							break;
						case '"':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset + 1;
							state = State.STRING;
							break;
						case '-':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset;
							state = State.BEFORE_INT;
							break;
						case '0':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset;
							state = State.AFTER_INT;
							break;
						default:
							if(c >= '1' && c <= '9') {
								stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
								start = offset;
								state = State.WITHIN_INT;
								break;
							}
							throw new MalformedJSONException("Expected value in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_ELEMENT:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case 'f':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_F;
							break;
						case 'n':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_N;
							break;
						case 't':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.NAME_T;
							break;
						case '{':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.BEFORE_INITIAL_KEY;
							sink.beginObject();
							break;
						case '[':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							state = State.BEFORE_INITIAL_ELEMENT;
							sink.beginArray();
							break;
						case '"':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset + 1;
							state = State.STRING;
							break;
						case '-':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset;
							state = State.BEFORE_INT;
							break;
						case '0':
							stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
							start = offset;
							state = State.AFTER_INT;
							break;
						default:
							if(c >= '1' && c <= '9') {
								stack.addLast(State.BEFORE_ELEMENT_SEPARATOR);
								start = offset;
								state = State.WITHIN_INT;
								break;
							}
							throw new MalformedJSONException("Expected value in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_ELEMENT_SEPARATOR:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						case ',':
							state = State.BEFORE_ELEMENT;
							break;
						case ']':
							if(stack.isEmpty())
								state = State.AFTER_DOCUMENT;
							else
								state = stack.removeLast();
							sink.endArray();
							break;
						default:
							throw new MalformedJSONException("Expected ',' to separate "
									+ "array elements or ']' to end array in line " + line + ", not code "
									+ (int)c, line);
					}
					break;
				case BEFORE_INT:
					if(c == '0')
						state = State.AFTER_INT;
					else if(c >= '1' && c <= '9')
						state = State.WITHIN_INT;
					else
						throw new MalformedJSONException("Expected digit after '-' in line " + line
								+ ", not code " + (int)c, line);
					break;
				case WITHIN_INT:
					switch(c) {
						case '.':
							state = State.BEFORE_FRACTION;
							break;
						case 'e':
						case 'E':
							state = State.BEFORE_EXPONENT_SIGN;
							break;
						default:
							if(c >= '0' && c <= '9')
								break;
							state = stack.removeLast();
							if(string == null)
								sink.foundInteger(Integer.parseInt(String.valueOf(data, start, offset-- - start)));
							else {
								string.append(data, start, offset-- - start);
								String s = string.toString();
								string = null;
								sink.foundInteger(Integer.parseInt(s));
							}
							break;
					}
					break;
				case AFTER_INT:
					switch(c) {
						case '.':
							state = State.BEFORE_FRACTION;
							break;
						case 'e':
						case 'E':
							state = State.BEFORE_EXPONENT_SIGN;
							break;
						default:
							state = stack.removeLast();
							if(string == null)
								sink.foundInteger(Integer.parseInt(String.valueOf(data, start, offset-- - start)));
							else {
								string.append(data, start, offset-- - start);
								String s = string.toString();
								string = null;
								sink.foundInteger(Integer.parseInt(s));
							}
							break;
					}
					break;
				case BEFORE_FRACTION:
					if(c >= '0' && c <= '9')
						state = State.WITHIN_FRACTION;
					else
						throw new MalformedJSONException("Expected digit after '.' in line " + line
								+ ", not code " + (int)c, line);
					break;
				case WITHIN_FRACTION:
					if(c == 'e' || c == 'E')
						state = State.BEFORE_EXPONENT_SIGN;
					else if(c < '0' || c > '9') {
						state = stack.removeLast();
						if(string == null)
							sink.foundFraction(Double.parseDouble(String.valueOf(data, start, offset-- - start)));
						else {
							string.append(data, start, offset-- - start);
							String s = string.toString();
							string = null;
							sink.foundFraction(Double.parseDouble(s));
						}
					}
					break;
				case BEFORE_EXPONENT_SIGN:
					if(c == '+' || c == '-')
						state = State.BEFORE_EXPONENT;
					else if(c >= '0' && c <= '9')
						state = State.WITHIN_EXPONENT;
					else
						throw new MalformedJSONException("Expected '+', '-' or digit after 'e' in number in line "
								+ line + ", not code " + (int)c, line);
					break;
				case BEFORE_EXPONENT:
					if(c < '0' || c > '9')
						throw new MalformedJSONException("Expected digit after exponent sign in line " + line
								+ ", not code " + (int)c, line);
					state = State.WITHIN_EXPONENT;
					break;
				case WITHIN_EXPONENT:
					if(c >= '0' && c <= '9')
						break;
					state = stack.removeLast();
					if(string == null)
						sink.foundFraction(Double.parseDouble(String.valueOf(data, start, offset-- - start)));
					else {
						string.append(data, start, offset-- - start);
						String s = string.toString();
						string = null;
						sink.foundFraction(Double.parseDouble(s));
					}
					break;
				case AFTER_DOCUMENT:
					switch(c) {
						case '\n':
							++line;
						case ' ':
						case '\t':
						case '\r':
							break;
						default:
							throw new MalformedJSONException("Expected end of document in line " + line
									+ ", not code " + (int)c, line);
					}
					break;
				case NAME_F:
					if(c != 'a')
						throw new MalformedJSONException("Expected 'alse' after 'f' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_FA;
					break;
				case NAME_FA:
					if(c != 'l')
						throw new MalformedJSONException("Expected 'lse' after 'fa' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_FAL;
					break;
				case NAME_FAL:
					if(c != 's')
						throw new MalformedJSONException("Expected 'se' after 'fal' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_FALS;
					break;
				case NAME_FALS:
					if(c != 'e')
						throw new MalformedJSONException("Expected 'e' after 'fals' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_FALSE;
					break;
				case NAME_FALSE:
					if(c == '_' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')
						throw new MalformedJSONException("Expected end of name after 'false' in line "
								+ line + ", not code " + (int)c, line);
					--offset;
					state = stack.removeLast();
					sink.foundBoolean(false);
					break;
				case NAME_T:
					if(c != 'r')
						throw new MalformedJSONException("Expected 'rue' after 't' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_TR;
					break;
				case NAME_TR:
					if(c != 'u')
						throw new MalformedJSONException("Expected 'ue' after 'tr' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_TRU;
					break;
				case NAME_TRU:
					if(c != 'e')
						throw new MalformedJSONException("Expected 'e' after 'tru' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_TRUE;
					break;
				case NAME_TRUE:
					if(c == '_' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')
						throw new MalformedJSONException("Expected end of name after 'true' in line "
								+ line + ", not code " + (int)c, line);
					--offset;
					state = stack.removeLast();
					sink.foundBoolean(true);
					break;
				case NAME_N:
					if(c != 'u')
						throw new MalformedJSONException("Expected 'ull' after 'n' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_NU;
					break;
				case NAME_NU:
					if(c != 'l')
						throw new MalformedJSONException("Expected 'll' after 'nu' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_NUL;
					break;
				case NAME_NUL:
					if(c != 'l')
						throw new MalformedJSONException("Expected 'l' after 'nul' in line " + line
								+ ", not code " + (int)c, line);
					state = State.NAME_NULL;
					break;
				case NAME_NULL:
					if(c == '_' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9')
						throw new MalformedJSONException("Expected end of name after 'null' in line "
								+ line + ", not code " + (int)c, line);
					--offset;
					state = stack.removeLast();
					sink.foundNull();
					break;
				default:
					throw new AssertionError("Unrecognized state: " + state.name());
			}
		}
		switch(state) {
			case STRING:
			case BEFORE_INT:
			case WITHIN_INT:
			case AFTER_INT:
			case BEFORE_FRACTION:
			case WITHIN_FRACTION:
			case BEFORE_EXPONENT_SIGN:
			case BEFORE_EXPONENT:
			case WITHIN_EXPONENT:
				if(offset > start) {
					if(string == null)
						string = new StringBuilder();
					string.append(data, start, offset - start);
				}
				break;
			case BEFORE_DOCUMENT:
			case NONE:
			case STRING_ESCAPE:
			case STRING_UNICODE:
			case NAME_F:
			case NAME_FA:
			case NAME_FAL:
			case NAME_FALS:
			case NAME_FALSE:
			case NAME_T:
			case NAME_TR:
			case NAME_TRU:
			case NAME_TRUE:
			case NAME_N:
			case NAME_NU:
			case NAME_NUL:
			case NAME_NULL:
			case BEFORE_INITIAL_KEY:
			case BEFORE_KEY:
			case BEFORE_NAME_SEPARATOR:
			case BEFORE_VALUE:
			case BEFORE_MEMBER_SEPARATOR:
			case BEFORE_INITIAL_ELEMENT:
			case BEFORE_ELEMENT:
			case BEFORE_ELEMENT_SEPARATOR:
			case AFTER_DOCUMENT:
				break;
			default:
				throw new AssertionError("Unrecognized state: " + state.name());
		}
	}

	public void endDocument() throws MalformedJSONException {
		if(state != State.AFTER_DOCUMENT)
			throw new MalformedJSONException("Unexpected end of document in line " + line, line);
	}

	public void pullSerial(Reader in) throws IOException, MalformedJSONException {
		char[] buffer = new char[1024];
		for(;;) {
			int count = in.read(buffer);
			if(count < 0)
				break;
			pushSerial(buffer, 0, count);
		}
		endDocument();
	}

	public void pullSerial(InputStream in) throws IOException, MalformedJSONException {
		pullSerial(new InputStreamReader(in, "UTF-8"));
	}

	public void pullSerial(InputStream in, String charset) throws IOException, MalformedJSONException {
		pullSerial(new InputStreamReader(in, charset == null ? "UTF-8" : charset));
	}

	public void pullSerial(File file) throws IOException, MalformedJSONException {
		FileInputStream fis = new FileInputStream(file);
		try {
			pullSerial(new InputStreamReader(fis, "UTF-8"));
		}
		finally {
			fis.close();
		}
	}

	public void pullSerial(File file, String charset) throws IOException, MalformedJSONException {
		FileInputStream fis = new FileInputStream(file);
		try {
			pullSerial(new InputStreamReader(fis, charset == null ? "UTF-8" : charset));
		}
		finally {
			fis.close();
		}
	}

}
