package com.pluralsight.security.service;

import static com.pluralsight.security.util.AuthenticationUtil.getUsername;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.Type;
import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioCommandServiceNoSql implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository currencyRepository;
	
	@Override
	public void addTransactionToPortfolio(AddTransactionToPortfolioDto request) {
		Portfolio portfolio = portfolioRepository.findByUsername(getUsername());
		Transaction transaction = createTransactionEntity(request);
		portfolio.addTransaction(transaction);
		portfolioRepository.save(portfolio);
	}

	@PreFilter("hasRole('AMDIN') or filterObject.username == authentication.username")
	public void addTransactionToPortfolio(List<AddTransactionToPortfolioDto> transactionsDto) {
		Portfolio portfolio = portfolioRepository.findByUsername(getUsername());
		for(AddTransactionToPortfolioDto transactionDto : transactionsDto) {
			Transaction transaction = createTransactionEntity(transactionDto);
			portfolio.addTransaction(transaction);
		}
		portfolioRepository.save(portfolio);
	}
	
	@Override
	public void removeTransactionFromPortfolio(String transactionId) {
		Portfolio portfolio = portfolioRepository.findByUsername(getUsername());
		Transaction transacion = portfolio.getTransactionById(transactionId);
		portfolio.deleteTransaction(transacion);
		portfolioRepository.save(portfolio);
	}
	
	@Override
	public void createNewPortfolio(String username) {
		this.portfolioRepository.save(new Portfolio(username, new ArrayList<>()));
	}
	
	private Transaction createTransactionEntity(AddTransactionToPortfolioDto request) {
		CryptoCurrency crpytoCurrency = currencyRepository.findBySymbol(request.getCryptoSymbol());
		Type type = Type.valueOf(request.getType());
		BigDecimal quantity = new BigDecimal(request.getQuantity());
		BigDecimal price = new BigDecimal(request.getPrice());
		Transaction transaction = new Transaction(crpytoCurrency, type, quantity, price,System.currentTimeMillis());
		return transaction;
	}

	@Override
	public boolean userHasAportfolio(String username) {
		return this.portfolioRepository.existsByUsername(username);
	}

}
