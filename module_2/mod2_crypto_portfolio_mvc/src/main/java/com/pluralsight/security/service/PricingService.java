package com.pluralsight.security.service;

import java.math.BigDecimal;

public interface PricingService {

	BigDecimal getCurrentPriceForCrypto(String symbol);
	
}