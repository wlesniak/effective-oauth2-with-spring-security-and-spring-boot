package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.model.CryptoCurrencyDto;

public interface CurrencyQueryService {

	List<CryptoCurrencyDto> getSupportedCryptoCurrencies();
	CryptoCurrencyDto getCryptoCurrency(String symbol);
}
