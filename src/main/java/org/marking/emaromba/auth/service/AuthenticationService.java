package org.marking.emaromba.auth.service;

import java.io.Serializable;

public interface AuthenticationService<T extends Serializable> {
	
	public String authenticate(T object);
	
}
