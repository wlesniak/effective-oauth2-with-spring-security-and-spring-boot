package com.pluralsight.security.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DummyPricingService implements PricingService {

	@Override
	public BigDecimal getCurrentPriceForCrypto(String symbol) {
		if(symbol.equals("BTC")) {
			return new BigDecimal("8000.00");
		} else {
			return new BigDecimal("100.00");
		}
	}

}
