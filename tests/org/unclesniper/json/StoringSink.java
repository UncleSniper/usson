package org.unclesniper.json;

import java.util.List;
import java.util.ArrayList;

public class StoringSink implements JSONSink {

	public enum CallKind {

		FOUND_BOOLEAN,
		FOUND_NULL,
		FOUND_STRING,
		FOUND_INTEGER,
		FOUND_FRACTION,
		BEGIN_OBJECT,
		END_OBJECT,
		BEGIN_ARRAY,
		END_ARRAY;

	}

	public static abstract class Call {

		public abstract CallKind getKind();

	}

	public static class NilaryCall extends Call {

		private CallKind kind;

		NilaryCall(CallKind kind) {
			this.kind = kind;
		}

		public CallKind getKind() {
			return kind;
		}

	}

	public static class FoundBooleanCall extends Call {

		public final boolean value;

		public FoundBooleanCall(boolean value) {
			this.value = value;
		}

		public CallKind getKind() {
			return CallKind.FOUND_BOOLEAN;
		}

	}

	public static class FoundStringCall extends Call {

		public final String value;

		public FoundStringCall(String value) {
			this.value = value;
		}

		public CallKind getKind() {
			return CallKind.FOUND_STRING;
		}

	}

	public static class FoundIntegerCall extends Call {

		public final long value;

		public FoundIntegerCall(long value) {
			this.value = value;
		}

		public CallKind getKind() {
			return CallKind.FOUND_INTEGER;
		}

	}

	public static class FoundFractionCall extends Call {

		public final double value;

		public FoundFractionCall(double value) {
			this.value = value;
		}

		public CallKind getKind() {
			return CallKind.FOUND_FRACTION;
		}

	}

	private static final Call[] CALL_ARRAY_TEMPLATE = new Call[0];

	private List<Call> calls = new ArrayList<Call>();

	public void foundBoolean(boolean value) {
		calls.add(new FoundBooleanCall(value));
	}

	public void foundNull() {
		calls.add(new NilaryCall(CallKind.FOUND_NULL));
	}

	public void foundString(String value) {
		calls.add(new FoundStringCall(value));
	}

	public void foundInteger(long value) {
		calls.add(new FoundIntegerCall(value));
	}

	public void foundFraction(double value) {
		calls.add(new FoundFractionCall(value));
	}

	public void beginObject() {
		calls.add(new NilaryCall(CallKind.BEGIN_OBJECT));
	}

	public void endObject() {
		calls.add(new NilaryCall(CallKind.END_OBJECT));
	}

	public void beginArray() {
		calls.add(new NilaryCall(CallKind.BEGIN_ARRAY));
	}

	public void endArray() {
		calls.add(new NilaryCall(CallKind.END_ARRAY));
	}

	public Call[] getCalls() {
		return calls.toArray(StoringSink.CALL_ARRAY_TEMPLATE);
	}

}
