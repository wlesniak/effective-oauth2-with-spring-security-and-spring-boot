package com.pluralsight.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pluralsight.security.entity.Portfolio;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
	Portfolio findByUsername(String username);
	boolean existsByUsername(String username);
}
