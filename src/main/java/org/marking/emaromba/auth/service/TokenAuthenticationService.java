package org.marking.emaromba.auth.service;

import org.marking.emaromba.auth.domain.Account;

public class TokenAuthenticationService implements AuthenticationService<Account> {

	@Override
	public String authenticate(Account account) {
		
		final String token = JWTGeneratorService.from(account).generate();
		
		
		
		return null;
	}

}
