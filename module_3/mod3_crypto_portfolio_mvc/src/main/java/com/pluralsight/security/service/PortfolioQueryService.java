package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;

public interface PortfolioQueryService {

	PortfolioPositionsDto getPortfolioPositions();
	PortfolioPositionsDto getPortfolioPositions(String id);
	ListTransactionsDto getPortfolioTransactions();
	List<String> getPortfolioIds();
	PortfolioPositionsDto getPortfolioPositionsForUser(String username);
	
}
