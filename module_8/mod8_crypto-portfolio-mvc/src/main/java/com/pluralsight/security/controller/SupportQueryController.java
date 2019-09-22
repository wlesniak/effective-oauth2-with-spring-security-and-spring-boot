package com.pluralsight.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.PostDto;
import com.pluralsight.security.model.SupportQueryResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SupportQueryController {
		
	private static final String SUPPORT_SERVICE_DOMAIN = "http://localhost:8181";
	private final WebClient webClient;
	
	@GetMapping("/support")
	public ModelAndView getQueries(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		SupportQueryResponse[] userSupportQueries = this.webClient.get().uri(SUPPORT_SERVICE_DOMAIN+"/support/"+user.getPreferredUsername())
				  			 .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
							 .retrieve()
							 .bodyToMono(SupportQueryResponse[].class)
							 .block();
		return new ModelAndView("support","queries",userSupportQueries);
	}

	@GetMapping("/support/query/{id}")
	public ModelAndView getQuery(@PathVariable String id, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		SupportQueryResponse response = this.webClient.get().uri(SUPPORT_SERVICE_DOMAIN+"/support/query/"+id)
				   .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
				   .retrieve()
				   .bodyToMono(SupportQueryResponse.class)
				   .block();
		ModelAndView model = new ModelAndView("query","query",response);
		PostDto newPost = new PostDto();
		newPost.setResolve(response.isResolved());
		model.addObject("newPost",new PostDto());
		return model;
	}	
	
	@GetMapping("/support/compose")
	public ModelAndView createNewSupportQuery() {
		ModelAndView model = new ModelAndView();
		model.addObject("newQuery", new CreateSupportQueryRequest());
		model.setViewName("compose");
		return model;
	}
	
}
