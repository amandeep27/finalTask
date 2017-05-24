package com.web.util.exception;

public class PwdHashingException extends Exception {

	private static final long serialVersionUID = 5392367341583293295L;

	public PwdHashingException() {
		super();
	}

	public PwdHashingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PwdHashingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PwdHashingException(String message) {
		super(message);
	}

	public PwdHashingException(Throwable cause) {
		super(cause);
	}

}
