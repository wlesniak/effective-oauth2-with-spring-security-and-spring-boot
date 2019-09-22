package com.pluralsight.security.controller;

import java.net.URI;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.pluralsight.security.model.CreateSupportQueryRequest;
import com.pluralsight.security.model.Post;
import com.pluralsight.security.model.SupportQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(value= {"http://localhost:3000"})
public class SupportController {

	private final WebClient webClient;
	private static final String SUPPORT_SERVICE_DOMAIN = "http://localhost:8181";
	
	@GetMapping("/support")
	public SupportQuery[] getQueries(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		String username = principal.getToken().getClaimAsString("preferred_username");
		return this.webClient.get().uri(SUPPORT_SERVICE_DOMAIN+"/support/"+username)
							 .headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
							 .retrieve()
							 .bodyToMono(SupportQuery[].class)
							 .block();
	}

	@GetMapping("/support/query/{id}")
	public Post getQuery(@PathVariable String id, @AuthenticationPrincipal JwtAuthenticationToken principal) {
		return this.webClient.get().uri(SUPPORT_SERVICE_DOMAIN+"/support/query/"+id)
				   .headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
				   .retrieve()
				   .bodyToMono(Post.class)
				   .block();
	}	
	
	@PutMapping("/support")
	public void createNewQuery(@RequestBody CreateSupportQueryRequest request, @AuthenticationPrincipal JwtAuthenticationToken principal) {
		URI targetUri = UriComponentsBuilder.fromHttpUrl(SUPPORT_SERVICE_DOMAIN)
			.path("/support")
			.build().encode().toUri();
			request.setUsername(principal.getToken().getClaimAsString("preferred_username"));
			this.webClient.put()
			.uri(targetUri)
			.headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
			.body(BodyInserters.fromObject(request))
			.retrieve()
			.bodyToMono(Void.class)
			.block();
	}	

	@PostMapping("/support/query/{id}")
	public void postToQuery(@RequestBody Post post, @PathVariable String id, @AuthenticationPrincipal JwtAuthenticationToken principal) {
		URI targetUri = UriComponentsBuilder.fromHttpUrl(SUPPORT_SERVICE_DOMAIN)
				.path("/support/query/"+id)
				.build().encode().toUri();
		this.webClient.post()
		.uri(targetUri)
		.headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
		.body(BodyInserters.fromObject(post))
		.retrieve()
		.bodyToMono(Void.class)
		.block();
	}
	
}
