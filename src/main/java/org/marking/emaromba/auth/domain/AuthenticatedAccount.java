package org.marking.emaromba.auth.domain;

import java.io.Serializable;

public class AuthenticatedAccount implements Serializable {
	
	private final Account account;
	private final String key;
	
	public AuthenticatedAccount(Account account, String key) {
		this.account = account;
		this.key = key;
	}
	
	
	public Account getAccount() {
		return account;
	}
	
	public String getKey() {
		return key;
	}

	private static final long serialVersionUID = 1L;	
}
