package org.marking.emaromba.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.marking.emaromba.auth.domain.Account;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTGeneratorService implements TokenGeneratorService<Account> {
	
	private static final String TYPE = "JWT";
	
	private final Account account;
	
	private JWTGeneratorService(Account account) {
		this.account = account;
	}
	
	public static JWTGeneratorService from(Account account) {
		return new JWTGeneratorService(account);
	}
	
	
	@Override
	public String generate() {
		return Jwts.builder()
				.setHeaderParam("typ", TYPE)
				.setClaims(getClaims())
				.setSubject(getSubject())
				//.setIssuer(headers.getOrigin())
				.signWith(getDefaultAlgorithm(), getGeneratedKey())
				.compact();
	}
	
	public static String getGeneratedKey() {
		return null;
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
