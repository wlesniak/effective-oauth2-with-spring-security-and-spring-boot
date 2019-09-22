package com.pluralsight.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddTransactionToPortfolioDto {

	@NonNull
	private String cryptoSymbol;
	@NonNull
	private String quantity;
	@NonNull
	private String price;
	@NonNull
	private String type;
	private String username;
	
}
