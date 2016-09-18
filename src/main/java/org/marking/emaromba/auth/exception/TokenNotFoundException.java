package org.marking.emaromba.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TokenNotFoundException extends AuthenticationAPIException {

	public TokenNotFoundException() {
		super("Token not found");
	}
	
	private static final long serialVersionUID = 1L;
}
