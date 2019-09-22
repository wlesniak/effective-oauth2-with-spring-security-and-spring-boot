package com.pluralsight.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.pluralsight.security.model.SupportQueryResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SupportAdminQueryController {

	private static final String SUPPORT_SERVICE_DOMAIN = "http://localhost:8181";
	private final WebClient webClient;
	
	@GetMapping("/support/admin")
	public ModelAndView getSupportQueries(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		SupportQueryResponse[] userSupportQueries = this.webClient.get().uri(SUPPORT_SERVICE_DOMAIN+"/support/admin")
	  			 .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
				 .retrieve()
				 .bodyToMono(SupportQueryResponse[].class)
				 .block();
		return new ModelAndView("support","queries",userSupportQueries);
	}
	
}
