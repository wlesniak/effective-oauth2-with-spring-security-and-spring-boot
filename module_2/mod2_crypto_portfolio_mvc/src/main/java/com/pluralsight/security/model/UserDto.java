package com.pluralsight.security.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.pluralsight.security.validation.PasswordConfirmed;
import com.pluralsight.security.validation.PasswordPolicy;
import com.pluralsight.security.validation.UniqueEmail;
import com.pluralsight.security.validation.UniqueUsername;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@PasswordConfirmed
public class UserDto {
	
	@NotEmpty(message="Please enter your firstname")
	private String firstname;
	@NotEmpty(message="Please enter your lastname")
	private String lastname;
	@NotEmpty(message="Please enter a username")
	@UniqueUsername
	private String username;
	@NotEmpty(message="Please enter an email")
	@Email(message="Email is not valid")
	@UniqueEmail
	private String email;
	@NotEmpty(message="Please enter in a password")
	@PasswordPolicy
	private String password;
	@NotEmpty(message="Please confirm your password")
	private String confirmPassword;
	@Min(4)
	private int securityPin;
	@Min(4)
	private int confirmSecurityPin;

}
