package com.pluralsight.security.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
	
	private CryptoCurrencyDto cryptoCurrency;
	private BigDecimal quantity;
	private BigDecimal value;

}
