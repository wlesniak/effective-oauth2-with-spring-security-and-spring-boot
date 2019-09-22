package com.pluralsight.security.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.DeleteTransactionsDto;
import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.TransactionDetailsDto;
import com.pluralsight.security.service.PortfolioQueryService;
import com.pluralsight.security.service.PricingService;
import com.pluralsight.security.userdetails.CryptoAuthenticatedPrincipal;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PortfolioQueryController {

	private final PortfolioQueryService portfolioService;
	private final PricingService priceService;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/portfolio";
	}
	
	@GetMapping("/portfolio")
	public ModelAndView positions() {
		ModelAndView model = new ModelAndView();
		model.addObject("positionsResponse", portfolioService.getPortfolioPositions());
		model.addObject("transaction", new AddTransactionToPortfolioDto());
		return model;
	}
	
	@GetMapping("/portfolio/{id}")
	public ModelAndView userPositions(@PathVariable String id) {
		ModelAndView model = new ModelAndView();
		model.addObject("positionsResponse", portfolioService.getPortfolioPositions(id));
		model.addObject("transaction", new AddTransactionToPortfolioDto());
		model.setViewName("portfolio");
		return model;
	}
	
	@GetMapping("/price")
	public ResponseEntity<BigDecimal> priceOfBtc() {
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
				.body(priceService.getCurrentPriceForCrypto("BTC"));
	}
	
	@GetMapping(value = {"/portfolio/transactions","/portfolio/transactions/{symbol}"})
	public ModelAndView listTransactionsForPortfolio(@PathVariable Optional<String> symbol) {
		ListTransactionsDto transactions = portfolioService.getPortfolioTransactions();
		ModelAndView model = new ModelAndView();
		if(symbol.isPresent()) {
			List<TransactionDetailsDto> symbolTrans = transactions.getTransactions().stream().filter(trans -> symbol.get().equals(trans.getSymbol())).collect(Collectors.toList());
			model.addObject("transactions",symbolTrans);
		} else {
			model.addObject("transactions",transactions.getTransactions());
		}
		model.addObject("selected",new DeleteTransactionsDto());
		model.setViewName("transactions");
		return model;
	}
	
}