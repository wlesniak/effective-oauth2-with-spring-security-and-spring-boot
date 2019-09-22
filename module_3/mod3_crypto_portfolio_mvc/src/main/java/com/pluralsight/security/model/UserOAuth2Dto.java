package com.pluralsight.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserOAuth2Dto {

	private final String firstname;
	private final String lastname;
	private final String username;
	private final String email;

}
