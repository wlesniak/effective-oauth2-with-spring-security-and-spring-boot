package com.pluralsight.security.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioPositionsResponse {

	private List<Position> positions;
	private Map<String, String> cryptoCurrencies;
	
	public Position getPositionForCrypto(CryptoCurrency crypto) {
		Position position = null;
		for(Position pos : positions) {
			if(pos.getCryptoCurrency().equals(crypto)) {
				position = pos;
				break;
			}
		}
		return position;
	}
	
}
