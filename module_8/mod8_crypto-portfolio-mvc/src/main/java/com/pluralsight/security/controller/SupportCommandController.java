package com.pluralsight.security.controller;

import java.net.URI;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.PostDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SupportCommandController {

	private final WebClient webClient;
	private static final String SUPPORT_SERVICE_DOMAIN = "http://localhost:8181";
	
	@PutMapping("/support")
	public String createNewQuery(@ModelAttribute CreateSupportQueryRequest request, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		URI targetUri = UriComponentsBuilder.fromHttpUrl(SUPPORT_SERVICE_DOMAIN)
				.path("/support")
				.build().encode().toUri();
				request.setUsername(user.getPreferredUsername());
				this.webClient.put()
				.uri(targetUri)
	  			.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
				.body(BodyInserters.fromObject(request))
				.retrieve()
				.bodyToMono(Void.class)
				.block();
		return "redirect:/support";
	}

	@PostMapping("/support/query/{id}")
	public String postToQuery(@ModelAttribute PostDto postDto, @PathVariable String id, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		postDto.setQueryId(id);
		URI targetUri = UriComponentsBuilder.fromHttpUrl(SUPPORT_SERVICE_DOMAIN)
				.path("/support/query/"+id)
				.build().encode().toUri();
		this.webClient.post()
		.uri(targetUri)
		.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
		.body(BodyInserters.fromObject(postDto))
		.retrieve()
		.bodyToMono(Void.class)
		.block();
		return "redirect:/support/query/"+postDto.getQueryId();
	}
	
}
