package com.pluralsight.security.controller;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.pluralsight.security.model.AddTransactionToPortfolioRequest;
import com.pluralsight.security.model.DeleteTransactionsRequest;
import com.pluralsight.security.model.PortfolioPositionsResponse;
import com.pluralsight.security.model.TransactionDetails;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PortfolioController {

	private final WebClient webClient;
	
	private static final String PORTFOLIO_SERVICE_DOMAIN = "http://localhost:8180";
	
	@GetMapping(value = {"/portfolio"})
	public PortfolioPositionsResponse portfolioPositions(
			@AuthenticationPrincipal JwtAuthenticationToken principal) {
		String accessToken = principal.getToken().getTokenValue();
		URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
											.path("/portfolio")
											.queryParam("username", principal.getToken().getClaimAsString("preferred_username"))
											.build().toUri();
		return this.webClient.get()
							 .uri(targetUri)
							 .headers(header -> header.setBearerAuth(accessToken))
							 .retrieve()
							 .bodyToMono(PortfolioPositionsResponse.class)
							 .block();
		
	}
	
	@GetMapping(value = {"/portfolio/transactions","/portfolio/transactions/{symbol}"})
	public TransactionDetails[] getTransactionDetails(@PathVariable Optional<String> symbol, @AuthenticationPrincipal JwtAuthenticationToken principal) {
		
		String path = "/portfolio/transactions";
		if(symbol.isPresent()) {
			path+="/"+symbol;
		}
		URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
				.path(path)
				.queryParam("username", principal.getToken().getClaimAsString("preferred_username"))
				.build().encode().toUri();
		return this.webClient.get()
					  .uri(targetUri)
					  .headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
					  .retrieve()
					  .bodyToMono(TransactionDetails[].class)
					  .block();
	}
	
	@PostMapping("/portfolio/transactions")
	public void addTransactionToPortfolio(@RequestBody AddTransactionToPortfolioRequest request,@AuthenticationPrincipal JwtAuthenticationToken principal) {	
		URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
		.path("/portfolio/transactions")
		.build().encode().toUri();
		request.setUsername(principal.getToken().getClaimAsString("preferred_username"));
		this.webClient.post()
		.uri(targetUri)
		.headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
		.body(BodyInserters.fromObject(request))
		.retrieve()
		.bodyToMono(Void.class)
		.block();
	}
	
	@DeleteMapping("/portfolio/transactions")
	public void deleteTransactionFromPortfolio(@RequestBody DeleteTransactionsRequest request, @AuthenticationPrincipal JwtAuthenticationToken principal) {
		for(String transactionId : request.getId()) {
			URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
				.path("/portfolio/transactions/"+transactionId)
				.queryParam("username", principal.getToken().getClaimAsString("preferred_username"))					
				.build().encode().toUri();
				request.setUsername(principal.getToken().getClaimAsString("preferred_username"));
				this.webClient.delete()
				.uri(targetUri)
				.headers(headers -> headers.setBearerAuth(principal.getToken().getTokenValue()))
				.retrieve()
				.bodyToMono(Void.class)
				.block();
		}
	}
	
}
