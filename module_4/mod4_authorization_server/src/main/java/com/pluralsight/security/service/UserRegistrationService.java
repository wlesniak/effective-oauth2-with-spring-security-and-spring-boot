package com.pluralsight.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.model.UserDto;
import com.pluralsight.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	public void registerNewUser(UserDto user) {
		CryptoUser cryptUser = new CryptoUser(
				user.getUsername(), 
				user.getFirstname(), 
				user.getLastname(),
				user.getEmail(), 
				encoder.encode(user.getPassword())
		);
		repository.save(cryptUser);
	}
	
}
