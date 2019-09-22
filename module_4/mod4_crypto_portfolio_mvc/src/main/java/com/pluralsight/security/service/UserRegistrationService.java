package com.pluralsight.security.service;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoOauth2User;
import com.pluralsight.security.model.UserOAuth2Dto;
import com.pluralsight.security.repository.Oauth2UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	private final Oauth2UserRepository oauth2Repository;
	
	public void registerNewAuth2User(UserOAuth2Dto userDto) {
		CryptoOauth2User user = new CryptoOauth2User(userDto.getUsername(), 
													 userDto.getFirstname(),
													 userDto.getLastname(),
													 userDto.getEmail());
		oauth2Repository.save(user);
	}
	
}
