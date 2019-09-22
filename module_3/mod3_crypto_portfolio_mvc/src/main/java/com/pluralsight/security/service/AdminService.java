package com.pluralsight.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepository;
	
	//@PreAuthorize("hasRole('ADMIN')")
	public List<CryptoUser> getAllUsers() {
		return this.userRepository.findAll();
	}
	
}
