package org.unclesniper.json;

public class OMGWereDoomedError extends Error {

	public OMGWereDoomedError() {
		super("What.");
	}

	public OMGWereDoomedError(String message) {
		super(message);
	}

	public OMGWereDoomedError(Throwable cause) {
		super(cause == null ? null : cause.getMessage(), cause);
	}

	public OMGWereDoomedError(String message, Throwable cause) {
		super(message, cause);
	}

}
