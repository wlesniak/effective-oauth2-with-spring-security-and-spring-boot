package com.pluralsight.security.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PortfolioPositionsDto {

	private final String firstName;
	private final String lastname;
	private final List<PositionDto> positions;
	private final Map<String, String> cryptoCurrencies;
	
	public PositionDto getPositionForCrypto(CryptoCurrencyDto crypto) {
		PositionDto position = null;
		for(PositionDto pos : positions) {
			if(pos.getCryptoCurrency().equals(crypto)) {
				position = pos;
				break;
			}
		}
		return position;
	}
	
}
