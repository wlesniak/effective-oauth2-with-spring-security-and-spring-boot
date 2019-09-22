package com.pluralsight.security.service;

import java.math.BigDecimal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pluralsight.security.model.Price;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebClientPricingService implements PricingService{

	private final WebClient webClient;
	
	@Override
	public BigDecimal getCurrentPriceForCrypto(String symbol) {
		Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Price[] prices = webClient.get()
					   .uri("http://localhost:8184/prices")
					   //.header("Authorization", "Bearer "+"")
					   //.headers(header -> header.setBearerAuth(jwt.getTokenValue()))
					   //.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
					   .retrieve()
					   .bodyToMono(Price[].class)
					   .block();
		for(Price price : prices) {
			if(price.getSymbol().equals(symbol)) {
				return new BigDecimal(price.getPrice());
			}
		}
		return null;
	}

}
