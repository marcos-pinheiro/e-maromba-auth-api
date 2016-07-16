package org.marking.emaromba.auth.domain;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.collect.ImmutableSet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode
public class Account implements Serializable {
	
	private long id;
	
	@Email
	private String email;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String role;
	
	@NotEmpty
	private Set<String> permissions;
	
	
	public Set<String> getPermissions() {
		return ImmutableSet.copyOf(permissions);
	}
	
	
	private static final long serialVersionUID = 1L;
}
