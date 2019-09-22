package com.pluralsight.security.service;

import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;

public interface PortfolioQueryService {

	PortfolioPositionsDto getPortfolioPositionsForUser(String username);
	ListTransactionsDto getPortfolioTransactionsForUser(String username);
	boolean userHasAportfolio(String username);
}
