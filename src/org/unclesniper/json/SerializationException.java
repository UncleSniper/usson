package org.unclesniper.json;

public class SerializationException extends RuntimeException {

	public SerializationException(String message) {
		super(message);
	}

	public SerializationException(String message, Throwable cause) {
		super(message, cause);
	}

}
