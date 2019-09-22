package com.pluralsight.security.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class PositionDto {
	
	private final CryptoCurrencyDto cryptoCurrency;
	private final BigDecimal quantity;
	private final BigDecimal value;

}
