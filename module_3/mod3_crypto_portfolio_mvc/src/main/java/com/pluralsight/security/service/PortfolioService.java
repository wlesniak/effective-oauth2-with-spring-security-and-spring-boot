package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;

public interface PortfolioService {

	List<CryptoCurrency> getSupportedCryptoCurrencies();
	Portfolio getPortfolioForUsername(String username);
	PortfolioPositionsDto getPortfolioPositions(String username);
	void addTransactionToPortfolio(AddTransactionToPortfolioDto request);
	ListTransactionsDto getPortfolioTransactions(String username);
	void removeTransactionFromPortfolio(String username, String transactionId);
	void createNewPortfolio(String username);
}
