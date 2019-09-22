package com.pluralsight.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TransactionDetailsDto {

	private final String id;
	private final String symbol;
	private final String type;
	private final String quantity;
	private final String price;
	
}
