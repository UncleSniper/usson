package org.unclesniper.json.lens;

public class IntegerOutOfRangeException extends UnexpectedJSONException {

	public enum PrimitiveType {
		BYTE,
		SHORT,
		INT
	}

	private final long longValue;

	private final PrimitiveType primitiveType;

	public IntegerOutOfRangeException(long longValue, PrimitiveType primitiveType) {
		super("Integer out of range" + (primitiveType == null ? "" : " for type "
				+ primitiveType.name().toLowerCase()) + ": " + longValue);
		this.longValue = longValue;
		this.primitiveType = primitiveType;
	}

	public long getLongValue() {
		return longValue;
	}

	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}

}
