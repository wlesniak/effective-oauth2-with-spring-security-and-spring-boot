package com.pluralsight.security.entity;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Transaction {

	@Id
	private String id;
	@DBRef
	private final CryptoCurrency cryptoCurrency;
	private final Type type;
	private final BigDecimal quantity;
	private final BigDecimal price;
	private final long timestamp;
	
	public Transaction(CryptoCurrency cryptoCurrency, Type type, BigDecimal quantity, BigDecimal price, long timestamp) {
		this.cryptoCurrency=cryptoCurrency;
		this.type=type;
		this.quantity=quantity;
		this.price=price;
		this.timestamp=timestamp;
		this.id = new ObjectId().toHexString();
	}
	
}
