package org.marking.emaromba.auth.rest;

import java.util.concurrent.TimeUnit;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.exception.TokenNotFoundException;
import org.marking.emaromba.auth.service.TokenGeneratorService;
import org.marking.emaromba.auth.service.JWTGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//https://github.com/jwtk/jjwt
//https://stormpath.com/blog/jwt-java-create-verify

@RestController
public class AuthenticationResource {
	
	@Autowired
	private RedisTemplate<String, Account> redisTemplate;	
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Account account, @RequestHeader HttpHeaders headers, UriComponentsBuilder uriBuilder) {
		
		final TokenGeneratorService<Account> authenticationService = JWTGeneratorService.from(account);
		final String token = authenticationService.generate();
		
		
		redisTemplate.opsForValue().set(token, account);
		redisTemplate.expire(token, 1, TimeUnit.MINUTES);
		
		return ResponseEntity
				.created(uriBuilder.path("/auth/{token}").buildAndExpand(token).toUri())
				.header("Authorization", "Bearer " + token).build();
	}
	
	@RequestMapping(value = "/auth/{token}", method = RequestMethod.GET)
	public ResponseEntity<Account> get(@PathVariable("token") String token) throws TokenNotFoundException {
		
		Account account = redisTemplate.opsForValue().get(token);
		
		if(account == null) {
			throw new TokenNotFoundException();
		}		
		redisTemplate.expire(token, 1, TimeUnit.MINUTES);
		
		
		
		
		
		
		
		
		return ResponseEntity.ok(account);
	}
}
