package com.pluralsight.security.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class JwtGrantedAuthoritiesConverter extends JwtAuthenticationConverter {

	@Override
	protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
		Collection<GrantedAuthority> authorities =  super.extractAuthorities(jwt);
		if(jwt.containsClaim("roles") && jwt.getClaimAsStringList("roles").contains("portfolio-admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return authorities;
	}
	
}
