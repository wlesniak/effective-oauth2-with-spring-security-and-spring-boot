package com.pluralsight.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class TransactionDetails {

	private String id;
	private String symbol;
	private String transactionType;
	private String quantity;
	private String price;
	
}
