package org.marking.emaromba.auth.service;

import java.io.Serializable;

import org.marking.emaromba.auth.exception.AuthenticationAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public interface AuthenticationService<T extends Serializable> {
	
	public String authenticate(T object) throws AuthenticationAPIException;
	
	public T retrieveInformationById(String id) throws AuthenticationAPIException;
	
	public void removeAuthentication(String id) throws AuthenticationAPIException;
	
}
