package com.pluralsight.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pluralsight.security.entity.CryptoOauth2User;

public interface Oauth2UserRepository extends MongoRepository<CryptoOauth2User, String>{
	
}
