package org.marking.emaromba.auth.util;

import org.apache.commons.lang3.RandomStringUtils;

public final class KeyGenerator {
	
	public static final String randonKey() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
	
}
