package org.unclesniper.json.tool;

public class Lexer {

	private enum State {
		NONE,
		EQUAL,
		BAR,
		AMPERSAND,
		NAME,
		INT,
		DECIMAL_POINT,
		FLOAT,
		STRING,
		ESCAPE,
		STRING_UNICODE
	}

	private final String input;

	private final int length;

	private int offset;

	private State state = State.NONE;

	private int startOffset;

	private StringBuilder buffer;

	private char stringDelimiter;

	private int code;

	private int digits;

	public Lexer(String input) {
		this.input = input;
		length = input.length();
	}

	public String getInput() {
		return input;
	}

	private Token token(Token.Type type) {
		return new Token(type, null, startOffset);
	}

	private Token literal(Token.Type type) {
		String text = buffer.toString();
		buffer = null;
		return new Token(type, text, startOffset);
	}

	private void unexpected() throws UnexpectedCharacterException {
		throw new UnexpectedCharacterException(offset - 1);
	}

	public Token lex() throws LexicalException {
		while(offset < length) {
			char c = input.charAt(offset++);
			switch(state) {
				case NONE:
					startOffset = offset - 1;
					switch(c) {
						case ' ':
							break;
						case '(':
							return token(Token.Type.LEFT_ROUND);
						case ')':
							return token(Token.Type.RIGHT_ROUND);
						case '[':
							return token(Token.Type.LEFT_SQUARE);
						case ']':
							return token(Token.Type.RIGHT_SQUARE);
						case '{':
							return token(Token.Type.LEFT_CURLY);
						case '}':
							return token(Token.Type.RIGHT_CURLY);
						case '<':
							return token(Token.Type.LEFT_ANGLE);
						case '>':
							return token(Token.Type.RIGHT_ANGLE);
						case ',':
							return token(Token.Type.COMMA);
						case '=':
							state = State.EQUAL;
							break;
						case '.':
							return token(Token.Type.DOT);
						case '/':
							return token(Token.Type.SLASH);
						case ':':
							return token(Token.Type.COLON);
						case '?':
							return token(Token.Type.THIS);
						case '|':
							state = State.BAR;
							break;
						case '&':
							state = State.AMPERSAND;
							break;
						case '^':
							return token(Token.Type.BIT_XOR);
						case '+':
							return token(Token.Type.PLUS);
						case '-':
							return token(Token.Type.MINUS);
						case '*':
							return token(Token.Type.STAR);
						case '%':
							return token(Token.Type.PERCENT);
						case '!':
							return token(Token.Type.NOT);
						case '~':
							return token(Token.Type.TILDE);
						case '@':
							return token(Token.Type.AT);
						case '_':
							buffer = new StringBuilder();
							buffer.append(c);
							state = State.NAME;
							break;
						case '"':
						case '\'':
							buffer = new StringBuilder();
							stringDelimiter = c;
							state = State.STRING;
							break;
						default:
							if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
								buffer = new StringBuilder();
								buffer.append(c);
								state = State.NAME;
							}
							else if(c >= '0' && c <= '9') {
								buffer = new StringBuilder();
								buffer.append(c);
								state = State.INT;
							}
							else
								unexpected();
							break;
					}
					break;
				case EQUAL:
					state = State.NONE;
					if(c == '=')
						return token(Token.Type.EQUAL);
					--offset;
					return token(Token.Type.ASSIGN);
				case BAR:
					state = State.NONE;
					if(c == '|')
						return token(Token.Type.LOG_OR);
					--offset;
					return token(Token.Type.BIT_OR);
				case AMPERSAND:
					state = State.NONE;
					if(c == '&')
						return token(Token.Type.LOG_AND);
					--offset;
					return token(Token.Type.BIT_AND);
				case NAME:
					if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_')
						buffer.append(c);
					else {
						state = State.NONE;
						--offset;
						return literal(Token.typeForName(buffer.toString()));
					}
					break;
				case INT:
					if(c >= '0' && c <= '9')
						buffer.append(c);
					else if(c == '.')
						state = State.DECIMAL_POINT;
					else {
						state = State.NONE;
						--offset;
						return literal(Token.Type.INT);
					}
					break;
				case DECIMAL_POINT:
					if(c >= '0' && c <= '9') {
						state = State.FLOAT;
						buffer.append(c);
					}
					else {
						state = State.NONE;
						offset -= 2;
						return literal(Token.Type.INT);
					}
					break;
				case FLOAT:
					if(c >= '0' && c <= '9')
						buffer.append(c);
					else {
						state = State.NONE;
						--offset;
						return literal(Token.Type.FLOAT);
					}
					break;
				case STRING:
					switch(c) {
						case '"':
						case '\'':
							if(c == stringDelimiter) {
								state = State.NONE;
								return literal(Token.Type.STRING);
							}
							else
								buffer.append(c);
							break;
						case '\\':
							state = State.ESCAPE;
							break;
						default:
							buffer.append(c);
							break;
					}
					break;
				case ESCAPE:
					switch(c) {
						case '"':
						case '\\':
						case '/':
							buffer.append(c);
							state = State.STRING;
							break;
						case 'b':
							buffer.append('\b');
							state = State.STRING;
							break;
						case 'f':
							buffer.append('\f');
							state = State.STRING;
							break;
						case 'n':
							buffer.append('\n');
							state = State.STRING;
							break;
						case 'r':
							buffer.append('\r');
							state = State.STRING;
							break;
						case 't':
							buffer.append('\t');
							state = State.STRING;
							break;
						case 'u':
							digits = code = 0;
							state = State.STRING_UNICODE;
							break;
						default:
							unexpected();
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
						unexpected();
					if(++digits == 4) {
						buffer.append((char)code);
						state = State.STRING;
					}
					break;
				default:
					throw new Error("Unrecognized state: " + state.name());
			}
		}
		switch(state) {
			case NONE:
				return null;
			case EQUAL:
				state = State.NONE;
				return token(Token.Type.ASSIGN);
			case BAR:
				state = State.NONE;
				return token(Token.Type.BIT_OR);
			case AMPERSAND:
				state = State.NONE;
				return token(Token.Type.BIT_AND);
			case NAME:
				state = State.NONE;
				return literal(Token.typeForName(buffer.toString()));
			case INT:
				state = State.NONE;
				return literal(Token.Type.INT);
			case DECIMAL_POINT:
				state = State.NONE;
				--offset;
				return literal(Token.Type.INT);
			case FLOAT:
				state = State.NONE;
				return literal(Token.Type.FLOAT);
			case STRING:
			case ESCAPE:
			case STRING_UNICODE:
				throw new UnexpectedEndOfInputException(length, startOffset);
			default:
				throw new Error("Unrecognized state: " + state.name());
		}
	}

}
