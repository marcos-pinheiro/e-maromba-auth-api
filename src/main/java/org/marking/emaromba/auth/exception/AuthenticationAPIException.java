package org.marking.emaromba.auth.exception;

public class AuthenticationAPIException extends Exception {

	public AuthenticationAPIException() {
		super();
	}

	public AuthenticationAPIException(String message) {
		super(message);
	}
	
	public AuthenticationAPIException(Throwable throwable) {
		super(throwable);
	}
	
	public AuthenticationAPIException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	
	private static final long serialVersionUID = 1L;
}
