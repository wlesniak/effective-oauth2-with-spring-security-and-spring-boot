package com.pluralsight.security.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(exclude= {"id","name"})
@ToString
@Document
public class CryptoCurrency {
	@Id
	private String id;
	@Indexed(unique=true)
	private final String symbol;
	private final String name;
	
}
