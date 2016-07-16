package org.marking.emaromba.auth.domain;

import java.io.Serializable;

public class AuthenticatedAccount implements Serializable {
	
	private final Account account;
	private final String token;
	
	public AuthenticatedAccount(Account account, String token) {
		this.account = account;
		this.token = token;
	}
	
	
	public Account getAccount() {
		return account;
	}
	
	public String getToken() {
		return token;
	}

	private static final long serialVersionUID = 1L;	
}
