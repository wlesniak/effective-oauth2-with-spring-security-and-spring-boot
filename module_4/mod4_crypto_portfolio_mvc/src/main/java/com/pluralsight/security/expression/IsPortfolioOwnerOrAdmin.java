package com.pluralsight.security.expression;

import static com.pluralsight.security.util.AuthenticationUtil.getUsername;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IsPortfolioOwnerOrAdmin {

	public boolean check(String username) {
		
		if(hasRole("ROLE_ADMIN")) {
			return true;
		}
		if(hasRole("ROLE_USER")) {
			String currentUsername = getUsername();
			return username.equals(currentUsername);
		}
		return false;
	}
	
	private boolean hasRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			if(role.equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}
	
}
