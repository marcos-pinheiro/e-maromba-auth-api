package org.marking.emaromba.auth.rest;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.exception.AuthenticationAPIException;
import org.marking.emaromba.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationResource {

	@Autowired
	private AuthenticationService<Account> authenticationBasedTokenService;

	
	/**
	 * 
	 * @param account
	 * @return
	 * @throws AuthenticationAPIException 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Account account) throws AuthenticationAPIException {

		final String token = authenticationBasedTokenService.authenticate(account);

		return ResponseEntity
				.noContent().header("Authorization", token).build();
	}


	/**
	 * 
	 * @param authorization
	 * @return
	 * @throws AuthenticationAPIException 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ResponseEntity<Account> get(@RequestHeader("Authorization") String authorization) throws AuthenticationAPIException {

		final Account account = authenticationBasedTokenService.retrieveInformationById(authorization);

		return ResponseEntity
				.ok(account);
	}


	/**
	 * 
	 * @param headers
	 * @return
	 * @throws AuthenticationAPIException
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeToken(@RequestHeader("Authorization") String authorization) throws AuthenticationAPIException {

		final String token = authorization;
		authenticationBasedTokenService.removeAuthentication(token);

		return ResponseEntity
				.noContent().build();
	}
}
