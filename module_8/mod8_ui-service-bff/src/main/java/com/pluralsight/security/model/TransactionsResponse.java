package com.pluralsight.security.model;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class TransactionsResponse {

	private String username;
	private List<TransactionDetails> transactions;
	
}
