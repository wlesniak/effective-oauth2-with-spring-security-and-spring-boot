package com.pluralsight.security.userdetails;

public interface CryptoAuthenticatedPrincipal {

	String getFirstName();
	String getLastName();
	String getFirstAndLastName();
	String getEmail();
	
}
