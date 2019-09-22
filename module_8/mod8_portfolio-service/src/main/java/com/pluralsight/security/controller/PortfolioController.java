package com.pluralsight.security.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.TransactionDetailsDto;
import com.pluralsight.security.service.PortfolioCommandService;
import com.pluralsight.security.service.PortfolioQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioController {

	private final PortfolioQueryService portfolioService;
	private final PortfolioCommandService commandService;
	
	
	@GetMapping(value = {"/portfolio","/portfolio/{portfolioId}"})
	public PortfolioPositionsDto portfolioPositions(@AuthenticationPrincipal JwtAuthenticationToken token) {
		String username = token.getToken().getClaimAsString("preferred_username");
		return portfolioService.getPortfolioPositionsForUser(username);
	}
	
	@RequestMapping(method=RequestMethod.HEAD,value="/portfolio")
	public ResponseEntity<?> userPortfolioExists(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		if(this.portfolioService.userHasAportfolio(principal.getToken().getClaimAsString("preferred_username"))) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = {"/portfolio/transactions","/portfolio/transactions/{symbol}"})
	public List<TransactionDetailsDto> getTransactionDetails(@PathVariable Optional<String> symbol, @AuthenticationPrincipal JwtAuthenticationToken token) {
		String username = token.getToken().getClaimAsString("preferred_username");
		ListTransactionsDto transactions = portfolioService.getPortfolioTransactionsForUser(username);
		if(symbol.isPresent()) {
			return  transactions.getTransactions().stream().filter(trans -> symbol.get().equals(trans.getSymbol())).collect(Collectors.toList());
		} 
		return transactions.getTransactions();
	}
	
	@PostMapping("/portfolio/transactions")
	public void addTransactionToPortfolio(@RequestBody AddTransactionToPortfolioDto request,@AuthenticationPrincipal JwtAuthenticationToken token) {
		String username = token.getToken().getClaimAsString("preferred_username");
		request.setUsername(username);
		commandService.addTransactionToPortfolio(request);
	}
	
	@DeleteMapping("/portfolio/transactions/{id}")
	public void deleteTransactionFromPortfolio(@PathVariable String id, @AuthenticationPrincipal JwtAuthenticationToken token) {
		String username = token.getToken().getClaimAsString("preferred_username");
		commandService.removeTransactionFromPortfolio(username,id);
	}
	
	@PutMapping("/portfolio")
	public void createPortfolio(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		String username = principal.getToken().getClaimAsString("preferred_username");
		if(!this.portfolioService.userHasAportfolio(username)) {
			this.commandService.createNewPortfolio(username);
		}
	}
	
}
