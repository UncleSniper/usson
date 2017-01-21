package org.unclesniper.json.tool;

import java.util.Map;
import java.util.HashMap;

public class Token {

	public enum Type {

		NAME(true, false, "name"),
		INT(true, false, "integer literal"),
		FLOAT(true, false, "fraction literal"),
		STRING(true, false, "string literal"),
		LEFT_ROUND(false, false, "("),
		RIGHT_ROUND(false, false, ")"),
		LEFT_SQUARE(false, false, "["),
		RIGHT_SQUARE(false, false, "]"),
		LEFT_CURLY(false, false, "{"),
		RIGHT_CURLY(false, false, "}"),
		LEFT_ANGLE(false, false, "<"),
		RIGHT_ANGLE(false, false, ">"),
		COMMA(false, false, ","),
		ASSIGN(false, false, "="),
		DOT(false, false, "."),
		SLASH(false, false, "/"),
		COLON(false, false, ":"),
		THIS(false, false, "?"),
		LOG_OR(false, false, "||"),
		LOG_AND(false, false, "&&"),
		BIT_OR(false, false, "|"),
		BIT_XOR(false, false, "^"),
		BIT_AND(false, false, "&"),
		EQUAL(false, false, "=="),
		UNEQUAL(false, false, "!="),
		LT(false, true, "lt"),
		LE(false, true, "le"),
		GT(false, true, "gt"),
		GE(false, true, "ge"),
		PLUS(false, false, "+"),
		MINUS(false, false, "-"),
		STAR(false, false, "*"),
		DIV(false, true, "div"),
		PERCENT(false, false, "%"),
		NOT(false, false, "!"),
		TILDE(false, false, "~"),
		AT(false, false, "@"),
		TRUE(false, true, "true"),
		FALSE(false, true, "false"),
		NULL(false, true, "null");

		private final boolean literal;

		private final boolean keyword;

		private final String rendition;

		private Type(boolean literal, boolean keyword, String rendition) {
			this.literal = literal;
			this.keyword = keyword;
			this.rendition = rendition;
		}

		public boolean isLiteral() {
			return literal;
		}

		public boolean isKeyword() {
			return keyword;
		}

		public String getRendition() {
			return rendition;
		}

		public String render() {
			return literal ? rendition : '\'' + rendition + '\'';
		}

	}

	private static final Map<String, Type> KEYWORDS;

	static {
		KEYWORDS = new HashMap<String, Type>();
		for(Type type : Token.Type.values()) {
			if(type.isKeyword())
				KEYWORDS.put(type.getRendition(), type);
		}
	}

	private final Type type;

	private final String text;

	private final int offset;

	public Token(Type type, String text, int offset) {
		this.type = type;
		this.text = text;
		this.offset = offset;
	}

	public Type getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public int getOffset() {
		return offset;
	}

	public static Type typeForName(String name) {
		Type type = Token.KEYWORDS.get(name);
		return type == null ? Type.NAME : type;
	}

}
