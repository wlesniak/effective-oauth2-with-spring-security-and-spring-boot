package com.pluralsight.security.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public class CryptoOauth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

	private final OAuth2AuthorizationRequestResolver defaultResolver;

	public CryptoOauth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegRepo,
			String authorizationRequestBaseUri) {
		this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegRepo,
				authorizationRequestBaseUri);
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
		OAuth2AuthorizationRequest authRequest = this.defaultResolver.resolve(request);
		if(authRequest == null) {
			return null;
		}
		 Map<String,Object> additionalParameters = new HashMap<String,Object>();
	        additionalParameters.putAll(authRequest.getAdditionalParameters());
	        additionalParameters.put("prompt", "consent");
	        return OAuth2AuthorizationRequest.from(authRequest)
	        		.additionalParameters(additionalParameters).build();
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
		OAuth2AuthorizationRequest authRequest = this.defaultResolver.resolve(request,clientRegistrationId);
		if(authRequest == null) {
			return null;
		}
		 Map<String,Object> additionalParameters = new HashMap<String,Object>();
	        additionalParameters.putAll(authRequest.getAdditionalParameters());
	        additionalParameters.put("prompt", "consent");
	        return OAuth2AuthorizationRequest.from(authRequest)
	        		.additionalParameters(additionalParameters).build();
	}
}
