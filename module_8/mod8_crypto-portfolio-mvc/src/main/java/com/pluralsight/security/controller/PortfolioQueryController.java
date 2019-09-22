package com.pluralsight.security.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.DeleteTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.TransactionDetailsDto;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class PortfolioQueryController {

	private static final String PORTFOLIO_SERVICE_DOMAIN = "http://localhost:8180";
	private final WebClient webClient;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/portfolio";
	}
	
	@GetMapping("/portfolio")
	public ModelAndView positions(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		ModelAndView model = new ModelAndView();		
		URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
				.path("/portfolio")
				.build().toUri();
		PortfolioPositionsDto positions = this.webClient.get()
					  .uri(targetUri)
					  //.attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId("crypto-portfolio"))
					  //.headers(headers -> headers.setBearerAuth(client.getAccessToken().getTokenValue()))
					  .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
					  
					  .retrieve()
					  .bodyToMono(PortfolioPositionsDto.class)
					  .block();
		model.addObject("positionsResponse", positions);
		model.addObject("transaction", new AddTransactionToPortfolioDto());
		return model;
	}
	
	@GetMapping(value = {"/portfolio/transactions","/portfolio/transactions/{symbol}"})
	public ModelAndView listTransactionsForPortfolio(@PathVariable Optional<String> symbol,@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client, @AuthenticationPrincipal OidcUser user) {
		String path = "/portfolio/transactions";
		if(symbol.isPresent()) {
			path+="/"+symbol.get();
		}
		URI targetUri = UriComponentsBuilder.fromHttpUrl(PORTFOLIO_SERVICE_DOMAIN)
				.path(path)
				.queryParam("username", user.getPreferredUsername())
				.build().encode().toUri();
		TransactionDetailsDto[] transactions = this.webClient.get()
					  .uri(targetUri)
					  .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
					  .retrieve()
					  .bodyToMono(TransactionDetailsDto[].class)
					  .block();
		ModelAndView model = new ModelAndView();
		if(symbol.isPresent()) {
			List<TransactionDetailsDto> symbolTrans = Arrays.stream(transactions).filter(trans -> symbol.get().equals(trans.getSymbol())).collect(Collectors.toList());
			model.addObject("transactions",symbolTrans);
		} else {
			model.addObject("transactions",transactions);
		}
		model.addObject("selected",new DeleteTransactionsDto());
		model.setViewName("transactions");
		return model;
	}
	
}