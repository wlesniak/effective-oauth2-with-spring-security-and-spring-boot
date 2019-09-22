package com.pluralsight.security.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IsPortfolioOwnerOrAdmin {

	public boolean check(String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(hasRole("ROLE_ADMIN",authentication)) {
			return true;
		}
		if(hasRole("ROLE_USER",authentication)) {
			String currentUsername = authentication.getName();
			return username.equals(currentUsername);
		}
		return false;
	}
	
	private boolean hasRole(String role, Authentication authentication) {
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
}
