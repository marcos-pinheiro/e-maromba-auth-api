package org.marking.emaromba.auth.rest;

import org.marking.emaromba.auth.domain.Account;
import org.marking.emaromba.auth.exception.AuthenticationAPIException;
import org.marking.emaromba.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class AuthenticationResource {

	@Autowired
	private AuthenticationService<Account> authenticationBasedTokenService;

	
	/**
	 * 
	 * @param account
	 * @param headers
	 * @param uriBuilder
	 * @return
	 * @throws AuthenticationAPIException 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Account account, @RequestHeader HttpHeaders headers, UriComponentsBuilder uriBuilder) throws AuthenticationAPIException {

		final String token = authenticationBasedTokenService.authenticate(account);

		return ResponseEntity
				.created(uriBuilder.path("/auth/{token}").buildAndExpand(token).toUri())
				.header("Authorization", token).build();
	}


	/**
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationAPIException 
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public ResponseEntity<Account> get(@RequestHeader HttpHeaders headers) throws AuthenticationAPIException {

		final String authorization = headers.get("Authorization").get(0);

		final Account account = authenticationBasedTokenService.retrieveInformationById(authorization);

		return ResponseEntity.ok(account);
	}


	/**
	 * 
	 * @param headers
	 * @return
	 * @throws AuthenticationAPIException
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeToken(@RequestHeader HttpHeaders headers) throws AuthenticationAPIException {

		final String token = headers.get("Authorization").get(0);
		authenticationBasedTokenService.removeAuthentication(token);


		return ResponseEntity.noContent().build();
	}


	/**
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationAPIException 
	 */
	@RequestMapping(value = "/auth/{token}", method = RequestMethod.GET) //NOT WORK
	public ResponseEntity<Account> get(@PathVariable("token") String token) throws AuthenticationAPIException {

		Account account = authenticationBasedTokenService.retrieveInformationById(token);

		return ResponseEntity.ok(account);
	}
}
