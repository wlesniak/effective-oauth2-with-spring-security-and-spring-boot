package com.pluralsight.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

public class MFAUser extends User implements CryptoAuthenticatedPrincipal{

	public MFAUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	@Setter
	@Getter
	private String securityPin;
	@Setter
	@Getter
	private boolean totpEnabled;
	@Setter
	@Getter
	private String firstName;
	@Setter
	@Getter
	private String lastName;
	@Setter
	@Getter
	private String email;
	
	@Override
	public String getFirstAndLastName() {
		return firstName+" "+lastName;
	}

	@Override
	public String getEmail() {
		return email;
	}
}
