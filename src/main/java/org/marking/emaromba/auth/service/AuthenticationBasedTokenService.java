package org.marking.emaromba.auth.service;

import java.util.concurrent.TimeUnit;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.exception.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("authenticationBasedTokenService")
public class AuthenticationBasedTokenService implements AuthenticationService<Account> {

	private static final int TIME = 5;

	private RedisTemplate<String, Account> redisTemplate;
	

	@Autowired
	public AuthenticationBasedTokenService(RedisTemplate<String, Account> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	@Override
	public String authenticate(Account account) {

		TokenGeneratorService tokenGeneratorService = JWTGeneratorService.from(account);
				
		final String token 	= tokenGeneratorService.generate();

		this.putToken(token, account);
		
		return token;
	}


	@Override
	public Account retrieveInformationById(String token) throws TokenNotFoundException {
		
		Account account = redisTemplate.opsForValue().get(token);

		if(account == null) {
			throw new TokenNotFoundException();
		}
		renewToken(token);
		
		
		return account;
	}

	@Override
	public void removeAuthentication(String token) {
		redisTemplate.opsForValue().getOperations().delete(token);
	}
	
	
	void renewToken(String token) {
		redisTemplate.expire(token, TIME, TimeUnit.MINUTES);
	}
	
	void putToken(String token, Account account) {
		redisTemplate.opsForValue().set(token, account);
		renewToken(token);
	}
}
