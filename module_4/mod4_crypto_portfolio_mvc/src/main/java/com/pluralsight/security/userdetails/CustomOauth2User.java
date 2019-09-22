package com.pluralsight.security.userdetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomOauth2User implements OAuth2User, CryptoAuthenticatedPrincipal   {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String firstAndLastName;
	private List<GrantedAuthority> authorities =
	        AuthorityUtils.createAuthorityList("ROLE_USER");
	private Map<String, Object> attributes;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities; 
	}

	@Override
	public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("username", this.getUsername());
            this.attributes.put("email", this.getEmail());
            this.attributes.put("given_name", this.getFirstName());
            this.attributes.put("family_name", this.getLastName());
        }
        return attributes;
	}

	@Override
	public String getName() {
		return this.username;
	}
	
}
