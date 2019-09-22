package com.pluralsight.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KeycloakLogoutHandler extends SecurityContextLogoutHandler {
	
	private final WebClient webClient;
	private final OAuth2AuthorizedClientService autherizedClients;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {		
		super.logout(request, response, authentication);
		OAuth2AuthorizedClient client = this.autherizedClients.loadAuthorizedClient("crypto-portfolio", authentication.getName());
		String endSessionEndpointUrl = (String)client.getClientRegistration().getProviderDetails().getConfigurationMetadata().get("end_session_endpoint");
		OidcUser user = (OidcUser) authentication.getPrincipal();
		UriComponentsBuilder builder = UriComponentsBuilder 
				.fromUriString(endSessionEndpointUrl) 
				.queryParam("id_token_hint", user.getIdToken().getTokenValue());
		this.webClient.get().uri(builder.toUriString()).retrieve().bodyToMono(Void.class).block();		
	}
	
}
