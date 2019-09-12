package org.unclesniper.json.lens;

public class UnexpectedJSONStructureException extends UnexpectedJSONException {

	private final String unexpected;

	private final String expected;

	public UnexpectedJSONStructureException(String unexpected, String expected) {
		super("Unexpected " + unexpected + (expected == null ? "" : ", expected " + expected));
		this.unexpected = unexpected;
		this.expected = expected;
	}

	public String getUnexpected() {
		return unexpected;
	}

	public String getExpected() {
		return expected;
	}

}
