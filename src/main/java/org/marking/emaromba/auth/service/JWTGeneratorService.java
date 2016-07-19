package org.marking.emaromba.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.util.KeyGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTGeneratorService implements TokenGeneratorService {
	
	private static final String TYPE = "JWT";
	
	private final Account account;
	private final String key;
	
	private JWTGeneratorService(Account account, String key) {
		this.account = account;
		this.key = key;
	}
	
	public static JWTGeneratorService from(Account account) {
		return new JWTGeneratorService(account, KeyGenerator.randonKey());
	}
	
	
	@Override
	public String generate() {
		return Jwts.builder()
				.setHeaderParam("typ", TYPE)
				.setClaims(getClaims())
				.setSubject(getSubject())
				.signWith(getDefaultAlgorithm(), key)
				.compact();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	
	public static SignatureAlgorithm getDefaultAlgorithm() {
		return SignatureAlgorithm.HS256;
	}
	
	private String getSubject() {
		return String.valueOf(account.getId());
	}
	
	private Map<String, Object> getClaims() {
		final Map<String, Object> map = new HashMap<>();
		map.put("account", account);
		
		return map;
	}
}
