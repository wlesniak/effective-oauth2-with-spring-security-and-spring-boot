package com.pluralsight.security.entity;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@RequiredArgsConstructor
@Getter
@ToString
public class CryptoUser {
	
	@Id
	private String id;
	@NonNull
	@Indexed(unique=true)
	private final String username;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@Email
	@NonNull
	private String email;
	@NonNull
	private String password;
	@Setter
	private boolean verified;
	@NonNull
	private String securityPin;
	
}
