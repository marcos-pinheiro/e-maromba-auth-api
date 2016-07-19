package org.marking.emaromba.auth.service;

import java.util.concurrent.TimeUnit;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.domain.AuthenticatedAccount;
import org.marking.emaromba.auth.exception.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("authenticationBasedTokenService")
public final class AuthenticationBasedTokenService implements AuthenticationService<Account> {

	private static final int TIME = 5;


	private RedisTemplate<String, AuthenticatedAccount> redisTemplate;

	@Autowired
	public AuthenticationBasedTokenService(RedisTemplate<String, AuthenticatedAccount> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	@Override
	public String authenticate(Account account) {

		TokenGeneratorService tokenGeneratorService = JWTGeneratorService.from(account);
				
		final String token 	= tokenGeneratorService.generate();
		final String key	= tokenGeneratorService.getKey();

		//Trocar isso depois
		redisTemplate.opsForValue().set(token, new AuthenticatedAccount(account, key));
		renewToken(token);
		
		return token;
	}


	@Override
	public Account retrieveInformationById(String token) throws TokenNotFoundException {
		
		AuthenticatedAccount authenticatedAccount = redisTemplate.opsForValue().get(token);

		if(authenticatedAccount == null) {
			throw new TokenNotFoundException();
		}
		renewToken(token);
		
		
		return authenticatedAccount.getAccount();
	}

	@Override
	public void removeAuthentication(String token) {
		redisTemplate.opsForValue().getOperations().delete(token);
		
	}
	
	
	void renewToken(String token) {
		redisTemplate.expire(token, TIME, TimeUnit.MINUTES);
	}
}
