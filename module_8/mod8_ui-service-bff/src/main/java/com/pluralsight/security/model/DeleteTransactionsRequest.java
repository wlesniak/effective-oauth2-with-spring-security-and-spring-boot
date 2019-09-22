package com.pluralsight.security.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DeleteTransactionsRequest {

	private String[] id;
	private String username;
}
