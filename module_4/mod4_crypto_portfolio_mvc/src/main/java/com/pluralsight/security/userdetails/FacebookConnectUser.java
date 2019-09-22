package com.pluralsight.security.userdetails;

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
public class FacebookConnectUser implements OAuth2User,CryptoAuthenticatedPrincipal {

	private String name;
	private String id;
	private String email;
	private List<GrantedAuthority> authorities =
	        AuthorityUtils.createAuthorityList("ROLE_USER");
	private Map<String, Object> attributes;
	
	@Override
	public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<String, Object>();
            this.attributes.put("id", this.getId());
            this.attributes.put("name", this.getName());
            this.attributes.put("email", this.getEmail());
            this.attributes.put("given_name", this.getName().split(" ")[0]);
            this.attributes.put("family_name", this.getName().split(" ")[1]);
        }
        return attributes;
	}


	@Override
	public String getFirstName() {
		return this.getAttributes().get("given_name").toString();
	}


	@Override
	public String getLastName() {
		return this.getAttributes().get("family_name").toString();
	}


	@Override
	public String getFirstAndLastName() {
		return getName();
	}	
	
}
