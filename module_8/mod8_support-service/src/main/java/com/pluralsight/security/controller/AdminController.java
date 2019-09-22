package com.pluralsight.security.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.model.SupportQueryResponse;
import com.pluralsight.security.service.SupportQueryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AdminController {
	
	private final SupportQueryService supportQueryService;
	
	@GetMapping("/support/admin")
	@PreAuthorize("hasRole('ADMIN') && hasAuthority('SCOPE_portfolio-service-admin')")
	public List<SupportQueryResponse> getSupportQueries() {
		return supportQueryService.getSupportQueriesForAllUsers();
	}
		
}
