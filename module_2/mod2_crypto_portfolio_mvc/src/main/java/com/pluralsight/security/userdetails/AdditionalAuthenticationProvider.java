package com.pluralsight.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdditionalAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
		AdditionalAuthenticationDetails details = (AdditionalAuthenticationDetails) authentication.getDetails();
		MFAUser user = (MFAUser) userDetails;		
		if(!getPasswordEncoder().matches(details.getSecurityPin(), user.getSecurityPin())) {
			throw new BadCredentialsException("Invalid security pin");
		}
		user.setSecurityPin(null);
	}
	
	@Override
	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	
	@Override
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}
	
}
