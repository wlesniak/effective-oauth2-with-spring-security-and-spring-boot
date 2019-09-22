package com.pluralsight.security.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Setter
public class Position {
	
	private CryptoCurrency cryptoCurrency;
	private BigDecimal quantity;
	private BigDecimal value;

}
