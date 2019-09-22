package com.pluralsight.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioService {

	private final WebClient webClient;
	private static final String PORTFOLIO_SERVICE_DOMAIN = "http://localhost:8180";
	
	public boolean userHasPortfolio(Jwt token) {
		ClientResponse response = this.webClient.head().uri(PORTFOLIO_SERVICE_DOMAIN+"/portfolio")
				.headers(headers -> headers.setBearerAuth(token.getTokenValue()))
							 .exchange()
							 .block();
		return response.statusCode().equals(HttpStatus.OK);
	}
	
	public void createPortfolio(Jwt token) {
		this.webClient.put().uri(PORTFOLIO_SERVICE_DOMAIN+"/portfolio")
		.headers(headers -> headers.setBearerAuth(token.getTokenValue()))
				 .exchange()
				 .block();
	}
	
}
